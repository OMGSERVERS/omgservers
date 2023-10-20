package com.omgservers.module.script.impl.luaRequest;

import lombok.ToString;
import org.luaj.vm2.LuaTable;

@ToString
abstract public class LuaRequest extends LuaTable {

    final String id;

    public LuaRequest(String id) {
        this.id = id;
        set("id", id);
    }

    public String getId() {
        return id;
    }
}
