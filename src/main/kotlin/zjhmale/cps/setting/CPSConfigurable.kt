package zjhmale.cps.setting

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

/**
 * Created by zjh on 16/3/22.
 */
class CPSConfigurable : Configurable {
    private var settingsForm: CPSSettingsForm? = null

    override fun createComponent(): JComponent? {
        settingsForm = settingsForm ?: CPSSettingsForm()
        return settingsForm?.component
    }

    override fun isModified() = settingsForm?.isModified ?: false

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings = CPSSettings.getInstance()

        settings.turnOnDef = settingsForm?.turnOnDef() ?: true
        settings.turnOnDefn = settingsForm?.turnOnDefn() ?: true
        settings.turnOnFn = settingsForm?.turnOnFn() ?: true
        settings.turnOnLambda = settingsForm?.turnOnLambda() ?: true
        settings.turnOnNotEqual = settingsForm?.turnOnNotEqual() ?: true
        settings.turnOnPartial = settingsForm?.turnOnPartial() ?: true
        settings.turnOnSet = settingsForm?.turnOnSet() ?: true
        settings.turnOnEmptySet = settingsForm?.turnOnEmptySet() ?: true
        settings.turnOnSetUnion = settingsForm?.turnOnSetUnion() ?: true
        settings.turnOnSetDifference = settingsForm?.turnOnSetDifference() ?: true
        settings.turnOnSetIntersection = settingsForm?.turnOnSetIntersection() ?: true
        settings.turnOnThreadFirst = settingsForm?.turnOnThreadFirst() ?: true
        settings.turnOnThreadLast = settingsForm?.turnOnThreadLast() ?: true
    }

    override fun reset() {
        settingsForm?.reset()
    }

    override fun disposeUIResources() {
        settingsForm = null
    }

    @Nls
    override fun getDisplayName() = "Clojure Pretty Symbol"

    override fun getHelpTopic() = null
}