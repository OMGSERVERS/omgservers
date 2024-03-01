package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand;

import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import org.luaj.vm2.LuaTable;

public interface LuaCommandMapper {

    LuaCommandQualifierEnum getQualifier();

    OutgoingCommandModel map(LuaTable luaCommand);
}
