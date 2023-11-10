package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import org.luaj.vm2.LuaTable;

public interface LuaCommandMapper {

    LuaCommandQualifierEnum getQualifier();

    DoCommandModel map(LuaContext luaContext, LuaTable luaCommand);
}
