package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;

public interface MapRuntimeCommandOperation {
    LuaCommand mapRuntimeCommand(LuaContext luaContext, RuntimeCommandModel runtimeCommand);
}
