package zjhmale.cps.setting;

import javax.swing.*;

/**
 * Created by zjh on 16/2/27.
 */
public class CPSSettingsForm {
    private JPanel appearancePanel;
    private JPanel panel;

    private JCheckBox prettyDefCheckBox;
    private JCheckBox prettyDefnCheckBox;
    private JCheckBox prettyFnCheckBox;
    private JCheckBox prettyLambdaCheckBox;
    private JCheckBox prettyNotEqualCheckBox;
    private JCheckBox prettyGTCheckBox;
    private JCheckBox prettyLTCheckBox;
    private JCheckBox prettyPartialCheckBox;
    private JCheckBox prettySetCheckBox;
    private JCheckBox prettyEmptySetCheckBox;
    private JCheckBox prettySetUnionCheckBox;
    private JCheckBox prettySetDifferenceCheckBox;
    private JCheckBox prettySetIntersectionCheckBox;
    private JCheckBox prettyThreadFirstCheckBox;
    private JCheckBox prettyThreadLastCheckBox;

    private final CPSSettings settings;

    public CPSSettingsForm() {
        prettyDefCheckBox.setSelected(true);
        prettyDefnCheckBox.setSelected(true);
        prettyFnCheckBox.setSelected(true);
        prettyLambdaCheckBox.setSelected(true);
        prettyNotEqualCheckBox.setSelected(true);
        prettyGTCheckBox.setSelected(true);
        prettyLTCheckBox.setSelected(true);
        prettyPartialCheckBox.setSelected(true);
        prettySetCheckBox.setSelected(true);
        prettyEmptySetCheckBox.setSelected(true);
        prettySetUnionCheckBox.setSelected(true);
        prettySetDifferenceCheckBox.setSelected(true);
        prettySetIntersectionCheckBox.setSelected(true);
        prettyThreadFirstCheckBox.setSelected(true);
        prettyThreadLastCheckBox.setSelected(true);

        settings = CPSSettings.getInstance();
    }

    public JComponent getComponent() {
        return panel;
    }

    public boolean turnOnDef() {
        return prettyDefCheckBox.isSelected();
    }

    public boolean turnOnDefn() {
        return prettyDefnCheckBox.isSelected();
    }

    public boolean turnOnFn() {
        return prettyFnCheckBox.isSelected();
    }

    public boolean turnOnLambda() {
        return prettyLambdaCheckBox.isSelected();
    }

    public boolean turnOnNotEqual() {
        return prettyNotEqualCheckBox.isSelected();
    }

    public boolean turnOnGT() {
        return prettyGTCheckBox.isSelected();
    }

    public boolean turnOnLT() {
        return prettyLTCheckBox.isSelected();
    }

    public boolean turnOnPartial() {
        return prettyPartialCheckBox.isSelected();
    }

    public boolean turnOnSet() {
        return prettySetCheckBox.isSelected();
    }

    public boolean turnOnEmptySet() {
        return prettyEmptySetCheckBox.isSelected();
    }

    public boolean turnOnSetUnion() {
        return prettySetUnionCheckBox.isSelected();
    }

    public boolean turnOnSetDifference() {
        return prettySetDifferenceCheckBox.isSelected();
    }

    public boolean turnOnSetIntersection() {
        return prettySetIntersectionCheckBox.isSelected();
    }

    public boolean turnOnThreadFirst() {
        return prettyThreadFirstCheckBox.isSelected();
    }

    public boolean turnOnThreadLast() {
        return prettyThreadLastCheckBox.isSelected();
    }

    public boolean isModified() {
        return prettyDefCheckBox.isSelected() != settings.turnOnDef
                || prettyDefnCheckBox.isSelected() != settings.turnOnDefn
                || prettyFnCheckBox.isSelected() != settings.turnOnFn
                || prettyLambdaCheckBox.isSelected() != settings.turnOnLambda
                || prettyNotEqualCheckBox.isSelected() != settings.turnOnNotEqual
                || prettyGTCheckBox.isSelected() != settings.turnOnGT
                || prettyLTCheckBox.isSelected() != settings.turnOnLT
                || prettyPartialCheckBox.isSelected() != settings.turnOnPartial
                || prettySetCheckBox.isSelected() != settings.turnOnSet
                || prettyEmptySetCheckBox.isSelected() != settings.turnOnEmptySet
                || prettySetUnionCheckBox.isSelected() != settings.turnOnSetUnion
                || prettySetDifferenceCheckBox.isSelected() != settings.turnOnSetDifference
                || prettySetIntersectionCheckBox.isSelected() != settings.turnOnSetIntersection
                || prettyThreadFirstCheckBox.isSelected() != settings.turnOnThreadFirst
                || prettyThreadLastCheckBox.isSelected() != settings.turnOnThreadLast;
    }

    public void reset() {
        prettyDefCheckBox.setSelected(settings.turnOnDef);
        prettyDefnCheckBox.setSelected(settings.turnOnDefn);
        prettyFnCheckBox.setSelected(settings.turnOnFn);
        prettyLambdaCheckBox.setSelected(settings.turnOnLambda);
        prettyNotEqualCheckBox.setSelected(settings.turnOnNotEqual);
        prettyGTCheckBox.setSelected(settings.turnOnGT);
        prettyLTCheckBox.setSelected(settings.turnOnLT);
        prettyPartialCheckBox.setSelected(settings.turnOnPartial);
        prettySetCheckBox.setSelected(settings.turnOnSet);
        prettyEmptySetCheckBox.setSelected(settings.turnOnEmptySet);
        prettySetUnionCheckBox.setSelected(settings.turnOnSetUnion);
        prettySetDifferenceCheckBox.setSelected(settings.turnOnSetDifference);
        prettySetIntersectionCheckBox.setSelected(settings.turnOnSetIntersection);
        prettyThreadFirstCheckBox.setSelected(settings.turnOnThreadFirst);
        prettyThreadLastCheckBox.setSelected(settings.turnOnThreadLast);
    }
}
