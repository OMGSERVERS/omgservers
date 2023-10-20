package com.omgservers.module.script.impl.operation.mapLuaCommand;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.script.ScriptModel;
import org.luaj.vm2.LuaTable;

public interface LuaCommandMapper {

    LuaCommandQualifierEnum getQualifier();

    EventModel map(ScriptModel script, LuaTable luaCommand);
}
