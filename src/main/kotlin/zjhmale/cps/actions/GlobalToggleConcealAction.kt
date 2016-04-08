package zjhmale.cps.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import zjhmale.cps.Util
import zjhmale.cps.setting.CPSSettings

/**
 * Created by zjh on 16/4/8.
 */
class GlobalToggleConcealAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent?) {
        val settings = CPSSettings.getInstance()
        settings.globalTurnOn = !settings.globalTurnOn

        Util.reopenCurrentFile()
    }
}