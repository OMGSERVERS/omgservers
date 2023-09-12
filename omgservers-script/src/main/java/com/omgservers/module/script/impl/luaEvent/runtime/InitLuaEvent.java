package com.omgservers.module.script.impl.luaEvent.runtime;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InitLuaEvent extends LuaEvent {

    public InitLuaEvent() {
        super("init");
    }
}
