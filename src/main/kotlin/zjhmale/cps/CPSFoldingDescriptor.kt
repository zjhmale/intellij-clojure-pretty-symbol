package zjhmale.cps

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange

/**
 * Created by zjh on 16/3/22.
 */
class CPSFoldingDescriptor(node: ASTNode,
                           range: TextRange,
                           group: FoldingGroup?,
                           val name: String,
                           val notExpandable: Boolean) : FoldingDescriptor(node, range, group) {

    override fun getPlaceholderText() = name

    override fun isNonExpandable() = notExpandable
}