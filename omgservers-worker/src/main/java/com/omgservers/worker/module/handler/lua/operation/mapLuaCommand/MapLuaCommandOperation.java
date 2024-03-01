package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand;

import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import org.luaj.vm2.LuaTable;

public interface MapLuaCommandOperation {
    OutgoingCommandModel mapLuaCommand(LuaTable luaCommand);
}
