package com.omgservers.module.script.impl.operation.mapScriptEvent;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.module.script.impl.event.LuaEvent;

public interface MapScriptEventOperation {
    LuaEvent mapScriptEvent(ScriptEventModel scriptEvent);
}
