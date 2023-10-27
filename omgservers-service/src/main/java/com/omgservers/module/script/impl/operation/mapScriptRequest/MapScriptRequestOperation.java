package com.omgservers.module.script.impl.operation.mapScriptRequest;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;

public interface MapScriptRequestOperation {
    LuaRequest mapScriptRequest(ScriptRequestModel scriptRequest);
}
