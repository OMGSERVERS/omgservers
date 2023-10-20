package com.omgservers.module.script.impl.operation.mapScriptRequest;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;

public interface ScriptRequestMapper {

    ScriptRequestQualifierEnum getQualifier();

    LuaRequest map(ScriptRequestModel scriptRequest);
}
