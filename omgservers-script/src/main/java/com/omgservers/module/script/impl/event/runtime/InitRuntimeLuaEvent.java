package com.omgservers.module.script.impl.event.runtime;

import com.omgservers.module.script.impl.event.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
@ToString
public class InitRuntimeLuaEvent extends LuaEvent {

    final LuaValue config;

    public InitRuntimeLuaEvent(final LuaValue config) {
        super("init_runtime");
        this.config = config;

        set("config", config);
    }
}
