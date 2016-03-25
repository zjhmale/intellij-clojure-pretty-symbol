package zjhmale.cps.setting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Created by zjh on 2016/2/16.
 */

@State(name = "CPSSettings", storages = {
        @Storage(id = "clojureprettysymbol_config", file = "$APP_CONFIG$/clojureprettysymbol_application.xml")
})
public class CPSSettings implements PersistentStateComponent<CPSSettings> {
    public boolean turnOnFn = true;
    public boolean turnOnPartial = true;
    public boolean turnOnDef = true;
    public boolean turnOnDefn = true;
    public boolean turnOnLet = true;
    public boolean turnOnLetfn = true;
    public boolean turnOnDoseq = true;
    public boolean turnOnComp = true;
    public boolean turnOnThreadFirst = true;
    public boolean turnOnThreadLast = true;
    public boolean turnOnNotEqual = true;
    public boolean turnOnGT = true;
    public boolean turnOnLT = true;
    public boolean turnOnAnd = true;
    public boolean turnOnOr = true;
    public boolean turnOnNot = true;
    public boolean turnOnLambda = true;
    public boolean turnOnSet = true;
    public boolean turnOnEmptySet = true;
    public boolean turnOnSetUnion = true;
    public boolean turnOnSetDifference = true;
    public boolean turnOnSetIntersection = true;

    @Nullable
    @Override
    public CPSSettings getState() {
        return this;
    }

    @Override
    public void loadState(CPSSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static CPSSettings getInstance() {
        return ServiceManager.getService(CPSSettings.class);
    }
}
