package com.omgservers.module.script.impl.operation.mapScriptEvent;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;

public interface MapScriptEventOperation {
    LuaEvent mapScriptEvent(ScriptEventModel scriptEvent);
}
