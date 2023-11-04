package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.worker.module.handler.lua.luaCommand.LuaCommand;

public interface MapRuntimeCommandOperation {
    LuaCommand mapRuntimeCommand(RuntimeCommandModel runtimeCommand);
}
