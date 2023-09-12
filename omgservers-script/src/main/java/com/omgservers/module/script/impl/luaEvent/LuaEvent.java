package com.omgservers.module.script.impl.luaEvent;

import lombok.ToString;
import org.luaj.vm2.LuaTable;

@ToString
abstract public class LuaEvent extends LuaTable {

    final String id;

    public LuaEvent(String id) {
        this.id = id;
        set("id", id);
    }

    public String getId() {
        return id;
    }
}
