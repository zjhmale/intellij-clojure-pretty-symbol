package zjhmale.cps;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjh on 2016/2/19.
 */
public class PrettySymbolFoldingBuilder implements FoldingBuilder {
    private static final Pattern symbolPattern = Pattern.compile("\\(fn|\\(partial|\\(->|\\(defn|not=|#\\(|#\\{");
    private static final HashMap<String, String> prettySymbolMaps;

    static {
        prettySymbolMaps = new HashMap<String, String>();
        prettySymbolMaps.put("(fn", "λ");
        prettySymbolMaps.put("(partial", "Ƥ");
        prettySymbolMaps.put("(defn", "ƒ");
        prettySymbolMaps.put("(->", "→");
        prettySymbolMaps.put("(->>", "⇉");
        prettySymbolMaps.put("not=", "≠");
        prettySymbolMaps.put("#(", "λ(");
        prettySymbolMaps.put("#{", "∈{");
    }


    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull final ASTNode node, @NotNull final Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        final String text = node.getText();
        final Matcher matcher = symbolPattern.matcher(text);

        while (matcher.find()) {
            final String key = text.substring(matcher.start(), matcher.end());
            final TextRange nodeRange = node.getTextRange();
            int rangeStart = nodeRange.getStartOffset() + matcher.start();
            int rangeEnd = nodeRange.getStartOffset() + matcher.end();
            String pretty = prettySymbolMaps.get(key);
            if (key.startsWith("(")) {
                rangeStart += 1;
            }
            if (key.startsWith("(->")) {
                String nextChar = text.substring(rangeEnd, rangeEnd + 1);
                if (nextChar.equals(">")) {
                    pretty = prettySymbolMaps.get("(->>");
                    rangeEnd += 1;
                }
            }

            final TextRange range = TextRange.create(rangeStart, rangeEnd);
            descriptors.add(new PrettySymbolFoldingDescriptor(node, range, null, pretty, true));
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
