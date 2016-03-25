package zjhmale.cps

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import zjhmale.cps.setting.CPSSettings
import java.util.*
import java.util.regex.Pattern

/**
 * Created by zjh on 16/3/22.
 */
class CPSFoldingBuilder : FoldingBuilder {
    private val symbolPattern = Pattern.compile(
            "\\(fn|\\(let|\\(partial|\\(->|\\(def|\\(doseq|\\(comp|not=|and|or|not|>=|<=|#\\(|#\\{|union|difference|intersection"
    )

    private val prettySymbolMaps = hashMapOf(
            "(fn" to "λ",
            "(let" to "┝",
            "(letfn" to "λ",
            "(partial" to "Ƥ",
            "(def" to "≡",
            "(defn" to "ƒ",
            "(doseq" to "",
            "(comp" to "∘",
            "(->" to "→",
            "(->>" to "⇉",
            "not=" to "≠",
            "and" to "∧",
            "or" to "∨",
            "not" to "¬",
            ">=" to "≥",
            "<=" to "≤",
            "#(" to "λ(",
            "#{" to "∈{",
            "#{}" to "∅",
            "union" to "⋃",
            "intersection" to "⋂",
            "difference" to "−"
    )
    private val openDelimiters = listOf("(", "{", "[")
    private val closeDelimiters = listOf(")", "}", "]")
    private val setOperators = listOf("union", "difference", "intersection")
    private val leftStopFlags = listOf("(", "[", " ", "/")

    private val GT = { a: Int, b: Int -> a > b }
    private val GE = { a: Int, b: Int -> a >= b }

    private fun isDelimiterMatch(text: String, start: Int, op: (Int, Int) -> Boolean): Boolean {
        var startOffset = start
        var nextChar = ""
        var leftCount = 0
        var rightCount = 0
        while (nextChar != "\n" && startOffset < text.length) {
            nextChar = text.substring(startOffset, startOffset + 1)
            if (openDelimiters.contains(nextChar)) {
                leftCount++
            }
            if (closeDelimiters.contains(nextChar)) {
                rightCount++
            }
            startOffset++
        }
        return op(rightCount, leftCount)
    }

    private fun findLeftStopPos(text: String, start: Int): Int {
        var startOffset = start
        var prevChar = ""
        while (!leftStopFlags.contains(prevChar) && startOffset > 0) {
            prevChar = text.substring(startOffset - 1, startOffset)
            startOffset--
        }
        if (leftStopFlags.contains(prevChar)) {
            return startOffset
        } else {
            return -1
        }
    }

    override fun buildFoldRegions(node: ASTNode, document: Document): Array<out FoldingDescriptor> {
        val settings = CPSSettings.getInstance()
        val descriptors = ArrayList<FoldingDescriptor>()
        val text = node.text
        val matcher = symbolPattern.matcher(text)

        while (matcher.find()) {
            var key = text.substring(matcher.start(), matcher.end())
            val nodeRange = node.textRange
            var rangeStart = nodeRange.startOffset + matcher.start()
            var rangeEnd = nodeRange.startOffset + matcher.end()
            if (key.startsWith("(")) {
                rangeStart += 1
            }

            val nextChar = text.substring(rangeEnd, rangeEnd + 1)
            val prevChar = text.substring(rangeStart - 1, rangeStart)

            val shouldFold =
                    if (key == "(def") {
                        if (nextChar == " ") {
                            settings.turnOnDef && isDelimiterMatch(text, rangeStart, GE)
                        } else if (nextChar == "n") {
                            key = "(defn"
                            rangeEnd += 1
                            settings.turnOnDefn
                        } else {
                            false
                        }

                    } else if (key == "(fn") {
                        settings.turnOnFn
                    } else if (key == "(partial") {
                        settings.turnOnPartial
                    } else if (key == "(->") {
                        if (nextChar == ">") {
                            key = "(->>"
                            rangeEnd += 1
                            settings.turnOnThreadLast && isDelimiterMatch(text, rangeStart, GT)
                        } else if (nextChar == " ") {
                            settings.turnOnThreadFirst && isDelimiterMatch(text, rangeStart, GT)
                        } else {
                            false
                        }
                    } else if (key == "(let") {
                        val nextTwoChars = text.substring(rangeEnd, rangeEnd + 2)
                        if (nextTwoChars == "fn") {
                            key = "(letfn"
                            rangeEnd += 2
                            settings.turnOnLetfn && isDelimiterMatch(text, rangeStart, GT)
                        } else if (nextChar == " ") {
                            settings.turnOnLet && isDelimiterMatch(text, rangeStart, GT)
                        } else {
                            false
                        }
                    } else if (key == "not=") {
                        settings.turnOnNotEqual && isDelimiterMatch(text, rangeStart, GT)
                    } else if (key == ">=") {
                        if (prevChar == ">") {
                            false
                        } else {
                            settings.turnOnGT && isDelimiterMatch(text, rangeStart, GT)
                        }
                    } else if (key == "<=") {
                        settings.turnOnLT && isDelimiterMatch(text, rangeStart, GT)
                    } else if (key == "#(") {
                        settings.turnOnLambda
                    } else if (key == "#{") {
                        if (nextChar == "}") {
                            key = "#{}"
                            rangeEnd += 1
                            settings.turnOnEmptySet
                        } else {
                            settings.turnOnSet
                        }
                    } else if (setOperators.contains(key)) {
                        val leftStopPos = findLeftStopPos(text, rangeStart)
                        if (leftStopPos != -1 && leftStopFlags.contains(prevChar)) {
                            rangeStart = leftStopPos + 1
                            if (nextChar == " " || nextChar == "]") {
                                if (key == "union") {
                                    settings.turnOnSetUnion
                                } else if (key == "difference") {
                                    settings.turnOnSetDifference
                                } else if (key == "intersection") {
                                    settings.turnOnSetIntersection
                                } else {
                                    false
                                }
                            } else {
                                false
                            }
                        } else {
                            false
                        }
                    } else {
                        false
                    }

            if (shouldFold) {
                val pretty = prettySymbolMaps[key] ?: return arrayOf<FoldingDescriptor>()
                val range = TextRange.create(rangeStart, rangeEnd)
                descriptors.add(CPSFoldingDescriptor(node, range, null, pretty, true))
            }
        }
        return descriptors.toArray<FoldingDescriptor>(arrayOfNulls<FoldingDescriptor>(descriptors.size))
    }

    override fun getPlaceholderText(node: ASTNode) = null

    override fun isCollapsedByDefault(node: ASTNode) = true
}