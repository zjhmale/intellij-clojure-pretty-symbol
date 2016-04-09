package zjhmale.cps

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.fileEditor.FileEditorManager

/**
 * Created by zjh on 16/4/8.
 */
object Util {
    fun reopenCurrentFile() {
        val dataContext = DataManager.getInstance().getDataContext()
        val project = DataKeys.PROJECT.getData(dataContext) ?: return

        val manager = FileEditorManager.getInstance(project)
        val selectedfiles = manager.selectedFiles
        val openFiles = manager.openFiles
        for (file in openFiles) {
            manager.closeFile(file)
            manager.openFile(file, false)
        }
        for (file in selectedfiles) {
            manager.openFile(file, true)
        }
    }
}