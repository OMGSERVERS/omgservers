package com.omgservers.module.script.impl.luaEvent.runtime;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
@ToString
public class InitLuaEvent extends LuaEvent {

    final LuaValue config;

    public InitLuaEvent(final LuaValue config) {
        super("init");
        this.config = config;

        set("config", config);
    }
}
