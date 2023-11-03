package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import org.luaj.vm2.LuaTable;

public interface MapLuaCommandOperation {
    DoCommandModel mapLuaCommand(LuaTable luaCommand);
}
