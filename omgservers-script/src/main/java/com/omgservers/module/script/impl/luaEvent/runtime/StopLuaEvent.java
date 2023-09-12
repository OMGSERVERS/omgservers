package com.omgservers.module.script.impl.luaEvent.runtime;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StopLuaEvent extends LuaEvent {

    public StopLuaEvent() {
        super("stop");
    }
}
