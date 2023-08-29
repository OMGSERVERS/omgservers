package com.omgservers.module.context.impl.luaEvent.runtime;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LuaUpdateRuntimeCommandReceivedEvent extends LuaEvent {

    final Long step;

    public LuaUpdateRuntimeCommandReceivedEvent(Long step) {
        super("runtime_update");
        this.step = step;
        set("step", step);
    }
}
