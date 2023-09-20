package com.omgservers.module.script.impl.event.runtime;

import com.omgservers.module.script.impl.event.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StopRuntimeLuaEvent extends LuaEvent {

    public StopRuntimeLuaEvent() {
        super("stop_runtime");
    }
}
