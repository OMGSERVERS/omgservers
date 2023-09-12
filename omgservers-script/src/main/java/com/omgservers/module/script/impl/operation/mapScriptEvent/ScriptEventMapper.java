package com.omgservers.module.script.impl.operation.mapScriptEvent;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;

public interface ScriptEventMapper {

    ScriptEventQualifierEnum getQualifier();

    LuaEvent map(ScriptEventModel scriptEvent);
}
