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

        settings.turnOnDef = settingsForm?.prettyDefCheckBox?.isSelected ?: true
        settings.turnOnDefn = settingsForm?.prettyDefnCheckBox?.isSelected ?: true
        settings.turnOnFn = settingsForm?.prettyFnCheckBox?.isSelected ?: true
        settings.turnOnLet = settingsForm?.prettyLetCheckBox?.isSelected ?: true
        settings.turnOnLetfn = settingsForm?.prettyLetfnCheckBox?.isSelected ?: true
        settings.turnOnDoseq = settingsForm?.prettyDoseqCheckBox?.isSelected ?: true
        settings.turnOnComp = settingsForm?.prettyCompCheckBox?.isSelected ?: true
        settings.turnOnLambda = settingsForm?.prettyLambdaCheckBox?.isSelected ?: true
        settings.turnOnNotEqual = settingsForm?.prettyNotEqualCheckBox?.isSelected ?: true
        settings.turnOnGT = settingsForm?.prettyGTCheckBox?.isSelected ?: true
        settings.turnOnLT = settingsForm?.prettyLTCheckBox?.isSelected ?: true
        settings.turnOnAnd = settingsForm?.prettyAndCheckBox?.isSelected ?: true
        settings.turnOnOr = settingsForm?.prettyOrCheckBox?.isSelected ?: true
        settings.turnOnNot = settingsForm?.prettyNotCheckBox?.isSelected ?: true
        settings.turnOnPartial = settingsForm?.prettyPartialCheckBox?.isSelected ?: true
        settings.turnOnSet = settingsForm?.prettySetCheckBox?.isSelected ?: true
        settings.turnOnEmptySet = settingsForm?.prettyEmptySetCheckBox?.isSelected ?: true
        settings.turnOnSetUnion = settingsForm?.prettySetCheckBox?.isSelected ?: true
        settings.turnOnSetDifference = settingsForm?.prettySetDifferenceCheckBox?.isSelected ?: true
        settings.turnOnSetIntersection = settingsForm?.prettySetIntersectionCheckBox?.isSelected ?: true
        settings.turnOnThreadFirst = settingsForm?.prettyThreadFirstCheckBox?.isSelected ?: true
        settings.turnOnThreadLast = settingsForm?.prettyThreadLastCheckBox?.isSelected ?: true
        settings.showUpInStringLiteral = settingsForm?.showUpInStringLiteralCheckBox?.isSelected ?: false
        settings.globalTurnOn = settingsForm?.globalTurnOnCheckBox?.isSelected ?: true
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