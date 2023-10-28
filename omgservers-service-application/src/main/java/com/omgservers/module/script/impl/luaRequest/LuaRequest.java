package com.omgservers.module.script.impl.luaRequest;

import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;

@Getter
@ToString
abstract public class LuaRequest extends LuaTable {

    final String qualifier;
    final Boolean infoLogging;

    public LuaRequest(final String qualifier, final Boolean infoLogging) {
        this.qualifier = qualifier;
        this.infoLogging = infoLogging;

        set("qualifier", qualifier);
    }
}
