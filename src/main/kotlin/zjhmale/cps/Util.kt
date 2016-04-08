package zjhmale.cps

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.ProjectManager

/**
 * Created by zjh on 16/4/8.
 */
object Util {
    fun reopenCurrentFile() {
        val project = ProjectManager.getInstance().openProjects[0]
        val manager = FileEditorManager.getInstance(project)
        val files = manager.selectedFiles
        for (file in files) {
            manager.closeFile(file)
            manager.openFile(file, true)
        }
    }
}