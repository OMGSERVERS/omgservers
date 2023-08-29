package com.omgservers.module.context.impl.luaEvent.runtime;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LuaStopRuntimeCommandReceivedEvent extends LuaEvent {

    public LuaStopRuntimeCommandReceivedEvent() {
        super("runtime_stop");
    }
}
