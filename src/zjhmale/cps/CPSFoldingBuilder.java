package zjhmale.cps;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zjhmale.cps.setting.CPSSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjh on 2016/2/19.
 */
public class CPSFoldingBuilder implements FoldingBuilder {
    private static final Pattern symbolPattern = Pattern.compile("\\(fn|\\(partial|\\(->|\\(def|not=|#\\(|#\\{|union|difference|intersection");
    private static final HashMap<String, String> prettySymbolMaps;
    private static final List<String> openDelimiters = Arrays.asList("(", "{", "[");
    private static final List<String> closeDelimiters = Arrays.asList(")", "}", "]");
    private static final List<String> setOperators = Arrays.asList("union", "difference", "intersection");

    static {
        prettySymbolMaps = new HashMap<String, String>();
        prettySymbolMaps.put("(fn", "λ");
        prettySymbolMaps.put("(partial", "Ƥ");
        prettySymbolMaps.put("(def", "≡");
        prettySymbolMaps.put("(defn", "ƒ");
        prettySymbolMaps.put("(->", "→");
        prettySymbolMaps.put("(->>", "⇉");
        prettySymbolMaps.put("not=", "≠");
        prettySymbolMaps.put("#(", "λ(");
        prettySymbolMaps.put("#{", "∈{");
        prettySymbolMaps.put("#{}", "∅");
        prettySymbolMaps.put("union", "⋃");
        prettySymbolMaps.put("intersection", "⋂");
        prettySymbolMaps.put("difference", "−");
    }

    private static boolean isContainOpenDelimiter(String text, int start) {
        boolean contain = false;
        String nextChar = "";
        while (!nextChar.equals("\n") && start < text.length()) {
            nextChar = text.substring(start, start + 1);
            if (openDelimiters.contains(nextChar)) {
                contain = true;
            }
            start++;
        }
        return contain;
    }

    private static boolean isDelimiterMatch(String text, int start) {
        String nextChar = "";
        int leftCount = 0;
        int rightCount = 0;
        while (!nextChar.equals("\n") && start < text.length()) {
            nextChar = text.substring(start, start + 1);
            if (openDelimiters.contains(nextChar)) {
                leftCount++;
            }
            if (closeDelimiters.contains(nextChar)) {
                rightCount++;
            }
            start++;
        }
        return rightCount > leftCount;
    }

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull final ASTNode node, @NotNull final Document document) {
        CPSSettings settings = CPSSettings.getInstance();
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        final String text = node.getText();
        final Matcher matcher = symbolPattern.matcher(text);

        while (matcher.find()) {
            String key = text.substring(matcher.start(), matcher.end());
            final TextRange nodeRange = node.getTextRange();
            int rangeStart = nodeRange.getStartOffset() + matcher.start();
            int rangeEnd = nodeRange.getStartOffset() + matcher.end();
            boolean shouldFold = false;
            if (key.startsWith("(")) {
                rangeStart += 1;
            }

            String nextChar = text.substring(rangeEnd, rangeEnd + 1);

            if (key.equals("(def")) {
                if (nextChar.equals(" ")) {
                    if (isContainOpenDelimiter(text, rangeStart)) {
                        shouldFold = settings.turnOnDef && isDelimiterMatch(text, rangeStart);
                    }
                }
                if (!nextChar.equals(" ")) {
                    shouldFold = false;
                }
                if (nextChar.equals("n")) {
                    key = "(defn";
                    rangeEnd += 1;
                    shouldFold = settings.turnOnDefn;
                }
            } else if (key.equals("(fn")) {
                shouldFold = settings.turnOnFn;
            } else if (key.equals("(partial")) {
                shouldFold = settings.turnOnPartial;
            } else if (key.equals("(->")) {
                if (nextChar.equals(">")) {
                    key = "(->>";
                    rangeEnd += 1;
                    shouldFold = settings.turnOnThreadLast;
                }
                if (nextChar.equals(" ")) {
                    shouldFold = settings.turnOnThreadFirst && isDelimiterMatch(text, rangeStart);
                }
            } else if (key.equals("not=")) {
                shouldFold = settings.turnOnNotEqual && isDelimiterMatch(text, rangeStart);
            } else if (key.equals("#(")) {
                shouldFold = settings.turnOnLambda;
            } else if (key.equals("#{")) {
                if (nextChar.equals("}")) {
                    key = "#{}";
                    rangeEnd += 1;
                    shouldFold = settings.turnOnEmptySet;
                } else {
                    shouldFold = settings.turnOnSet;
                }
            } else if (setOperators.contains(key)) {
                String nextThreeChars = text.substring(rangeEnd, rangeEnd + 3);
                if (nextThreeChars.equals(" #{")) {
                    if (key.equals("union")) {
                        shouldFold = settings.turnOnSetUnion;
                    }
                    if (key.equals("difference")) {
                        shouldFold = settings.turnOnSetDifference;
                    }
                    if (key.equals("intersection")) {
                        shouldFold = settings.turnOnSetIntersection;
                    }
                }
            }

            if (shouldFold) {
                String pretty = prettySymbolMaps.get(key);
                final TextRange range = TextRange.create(rangeStart, rangeEnd);
                descriptors.add(new CPSFoldingDescriptor(node, range, null, pretty, true));
            }
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }
}
