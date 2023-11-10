package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import org.luaj.vm2.LuaTable;

public interface MapLuaCommandOperation {
    DoCommandModel mapLuaCommand(LuaContext luaContext, LuaTable luaCommand);
}
