package zjhmale.cps;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by zjh on 2016/2/19.
 */
public class PrettySymbolFoldingDescriptor extends FoldingDescriptor {

    private final String foldedText;
    private final boolean isNonExpandable;

    public PrettySymbolFoldingDescriptor(final ASTNode node, final TextRange range, final FoldingGroup group, @NotNull final String name, final boolean isNonExpandable) {
        super(node, range, group);
        this.foldedText = name;
        this.isNonExpandable = isNonExpandable;
    }

    @Nullable
    @Override
    public String getPlaceholderText() {
        return this.foldedText;
    }

    @Override
    public boolean isNonExpandable() {
        return this.isNonExpandable;
    }
}
