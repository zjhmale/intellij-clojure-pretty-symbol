package zjhmale.cps.setting;

import javax.swing.*;

/**
 * Created by zjh on 16/2/27.
 */
public class CPSSettingsForm {
    private JPanel appearancePanel;
    private JPanel panel;

    public JCheckBox prettyDefCheckBox;
    public JCheckBox prettyDefnCheckBox;
    public JCheckBox prettyFnCheckBox;
    public JCheckBox prettyLambdaCheckBox;
    public JCheckBox prettyNotEqualCheckBox;
    public JCheckBox prettyGTCheckBox;
    public JCheckBox prettyLTCheckBox;
    public JCheckBox prettyPartialCheckBox;
    public JCheckBox prettySetCheckBox;
    public JCheckBox prettyEmptySetCheckBox;
    public JCheckBox prettySetUnionCheckBox;
    public JCheckBox prettySetDifferenceCheckBox;
    public JCheckBox prettySetIntersectionCheckBox;
    public JCheckBox prettyThreadFirstCheckBox;
    public JCheckBox prettyThreadLastCheckBox;
    public JCheckBox prettyLetCheckBox;
    public JCheckBox prettyLetfnCheckBox;
    public JCheckBox prettyDoseqCheckBox;
    public JCheckBox prettyCompCheckBox;
    public JCheckBox prettyAndCheckBox;
    public JCheckBox prettyOrCheckBox;
    public JCheckBox prettyNotCheckBox;

    private final CPSSettings settings;

    public CPSSettingsForm() {
        settings = CPSSettings.getInstance();
    }

    public JComponent getComponent() {
        return panel;
    }

    public boolean isModified() {
        return prettyDefCheckBox.isSelected() != settings.turnOnDef
                || prettyDefnCheckBox.isSelected() != settings.turnOnDefn
                || prettyFnCheckBox.isSelected() != settings.turnOnFn
                || prettyLetCheckBox.isSelected() != settings.turnOnLet
                || prettyLetfnCheckBox.isSelected() != settings.turnOnLetfn
                || prettyDoseqCheckBox.isSelected() != settings.turnOnDoseq
                || prettyCompCheckBox.isSelected() != settings.turnOnComp
                || prettyLambdaCheckBox.isSelected() != settings.turnOnLambda
                || prettyNotEqualCheckBox.isSelected() != settings.turnOnNotEqual
                || prettyGTCheckBox.isSelected() != settings.turnOnGT
                || prettyLTCheckBox.isSelected() != settings.turnOnLT
                || prettyAndCheckBox.isSelected() != settings.turnOnAnd
                || prettyOrCheckBox.isSelected() != settings.turnOnOr
                || prettyNotCheckBox.isSelected() != settings.turnOnNot
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
        prettyLetCheckBox.setSelected(settings.turnOnLet);
        prettyLetfnCheckBox.setSelected(settings.turnOnLetfn);
        prettyDoseqCheckBox.setSelected(settings.turnOnDoseq);
        prettyCompCheckBox.setSelected(settings.turnOnComp);
        prettyLambdaCheckBox.setSelected(settings.turnOnLambda);
        prettyNotEqualCheckBox.setSelected(settings.turnOnNotEqual);
        prettyGTCheckBox.setSelected(settings.turnOnGT);
        prettyLTCheckBox.setSelected(settings.turnOnLT);
        prettyAndCheckBox.setSelected(settings.turnOnAnd);
        prettyOrCheckBox.setSelected(settings.turnOnOr);
        prettyNotCheckBox.setSelected(settings.turnOnNot);
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
