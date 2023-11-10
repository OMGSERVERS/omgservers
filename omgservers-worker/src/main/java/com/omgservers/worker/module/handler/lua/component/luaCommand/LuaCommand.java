package com.omgservers.worker.module.handler.lua.component.luaCommand;

import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;

@Getter
@ToString
abstract public class LuaCommand extends LuaTable {

    final String qualifier;
    final Boolean infoLogging;

    public LuaCommand(final String qualifier, final Boolean infoLogging) {
        this.qualifier = qualifier;
        this.infoLogging = infoLogging;

        set("qualifier", qualifier);
    }
}
