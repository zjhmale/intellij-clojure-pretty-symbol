package zjhmale.cps

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import zjhmale.cps.setting.CPSSettings
import java.util.*
import java.util.regex.Pattern

/**
 * Created by zjh on 16/3/22.
 */
class CPSFoldingBuilder : FoldingBuilder {
    private val symbolPattern = Pattern.compile("\\(fn|\\(partial|\\(->|\\(def|not=|#\\(|#\\{|union|difference|intersection")
    private val prettySymbolMaps = hashMapOf(
            "(fn" to "λ",
            "(partial" to "Ƥ",
            "(def" to "≡",
            "(defn" to "ƒ",
            "(->" to "→",
            "(->>" to "⇉",
            "not=" to "≠",
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
    private val foldingGroup = FoldingGroup.newGroup("Clojure Pretty Symbol")

    private fun isDelimiterMatch(text: String, start: Int): Boolean {
        var start = start
        var nextChar = ""
        var leftCount = 0
        var rightCount = 0
        while (nextChar != "\n" && start < text.length) {
            nextChar = text.substring(start, start + 1)
            if (openDelimiters.contains(nextChar)) {
                leftCount++
            }
            if (closeDelimiters.contains(nextChar)) {
                rightCount++
            }
            start++
        }
        return rightCount > leftCount
    }

    private fun findLeftStopPos(text: String, start: Int): Int {
        var start = start
        var prevChar = ""
        while (!leftStopFlags.contains(prevChar) && start > 0) {
            prevChar = text.substring(start - 1, start)
            start--
        }
        if (leftStopFlags.contains(prevChar)) {
            return start
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
            var shouldFold = false
            if (key.startsWith("(")) {
                rangeStart += 1
            }

            val nextChar = text.substring(rangeEnd, rangeEnd + 1)
            val prevChar = text.substring(rangeStart - 1, rangeStart)

            if (key == "(def") {
                if (nextChar == " ") {
                    shouldFold = settings.turnOnDef && isDelimiterMatch(text, rangeStart)
                }
                if (nextChar != " ") {
                    shouldFold = false
                }
                if (nextChar == "n") {
                    key = "(defn"
                    rangeEnd += 1
                    shouldFold = settings.turnOnDefn
                }
            } else if (key == "(fn") {
                shouldFold = settings.turnOnFn
            } else if (key == "(partial") {
                shouldFold = settings.turnOnPartial
            } else if (key == "(->") {
                if (nextChar == ">") {
                    key = "(->>"
                    rangeEnd += 1
                    shouldFold = settings.turnOnThreadLast && isDelimiterMatch(text, rangeStart)
                }
                if (nextChar == " ") {
                    shouldFold = settings.turnOnThreadFirst && isDelimiterMatch(text, rangeStart)
                }
            } else if (key == "not=") {
                shouldFold = settings.turnOnNotEqual && isDelimiterMatch(text, rangeStart)
            } else if (key == "#(") {
                shouldFold = settings.turnOnLambda
            } else if (key == "#{") {
                if (nextChar == "}") {
                    key = "#{}"
                    rangeEnd += 1
                    shouldFold = settings.turnOnEmptySet
                } else {
                    shouldFold = settings.turnOnSet
                }
            } else if (setOperators.contains(key)) {
                val leftStopPos = findLeftStopPos(text, rangeStart)
                if (leftStopPos != -1 && leftStopFlags.contains(prevChar)) {
                    rangeStart = leftStopPos + 1
                    if (nextChar == " " || nextChar == "]") {
                        if (key == "union") {
                            shouldFold = settings.turnOnSetUnion
                        }
                        if (key == "difference") {
                            shouldFold = settings.turnOnSetDifference
                        }
                        if (key == "intersection") {
                            shouldFold = settings.turnOnSetIntersection
                        }
                    }
                }
            }

            if (shouldFold) {
                val pretty = prettySymbolMaps[key]!!
                val range = TextRange.create(rangeStart, rangeEnd)
                descriptors.add(CPSFoldingDescriptor(node, range, foldingGroup, pretty, true))
            }
        }
        return descriptors.toArray<FoldingDescriptor>(arrayOfNulls<FoldingDescriptor>(descriptors.size))
    }

    override fun getPlaceholderText(node: ASTNode) = null

    override fun isCollapsedByDefault(node: ASTNode) = true
}