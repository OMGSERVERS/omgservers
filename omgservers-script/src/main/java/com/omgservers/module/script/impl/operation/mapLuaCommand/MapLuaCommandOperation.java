package com.omgservers.module.script.impl.operation.mapLuaCommand;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.script.ScriptModel;
import org.luaj.vm2.LuaTable;

public interface MapLuaCommandOperation {
    EventModel mapLuaCommand(ScriptModel script, LuaTable luaCommand);
}
