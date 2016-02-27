package zjhmale.cps.setting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by zjh on 2016/2/16.
 */
public class CPSConfigurable implements Configurable {
    private CPSSettingsForm settingsForm;

    public CPSConfigurable() {
        super();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingsForm == null) {
            settingsForm = new CPSSettingsForm();
        }
        return settingsForm.getComponent();
    }

    @Override
    public boolean isModified() {
        return settingsForm.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        CPSSettings settings = CPSSettings.getInstance();

        settings.turnOnDef = settingsForm.turnOnDef();
        settings.turnOnDefn = settingsForm.turnOnDefn();
        settings.turnOnFn = settingsForm.turnOnFn();
        settings.turnOnLambda = settingsForm.turnOnLambda();
        settings.turnOnNotEqual = settingsForm.turnOnNotEqual();
        settings.turnOnPartial = settingsForm.turnOnPartial();
        settings.turnOnSet = settingsForm.turnOnSet();
        settings.turnOnThreadFirst = settingsForm.turnOnThreadFirst();
        settings.turnOnThreadLast = settingsForm.turnOnThreadLast();
    }

    @Override
    public void reset() {
        if (settingsForm != null) {
            settingsForm.reset();
        }
    }

    @Override
    public void disposeUIResources() {
        settingsForm = null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Clojure Pretty Symbol";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }
}
