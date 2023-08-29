package com.omgservers.module.context.impl.luaEvent.runtime;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LuaInitRuntimeCommandReceivedEvent extends LuaEvent {

    final Long runtimeId;

    public LuaInitRuntimeCommandReceivedEvent(final Long runtimeId) {
        super("runtime_init");
        this.runtimeId = runtimeId;
        set("runtime_id", runtimeId.toString());
    }
}
