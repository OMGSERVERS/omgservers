package com.omgservers.module.script.impl.luaRequest;

import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;

@Getter
@ToString
abstract public class LuaRequest extends LuaTable {

    final String id;
    final Boolean infoLogging;

    public LuaRequest(final String id, final Boolean infoLogging) {
        this.id = id;
        this.infoLogging = infoLogging;

        set("id", id);
    }
}
