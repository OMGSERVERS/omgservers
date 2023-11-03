package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;

public interface MapRuntimeCommandOperation {
    LuaRequest mapRuntimeCommand(RuntimeCommandModel runtimeCommand);
}
