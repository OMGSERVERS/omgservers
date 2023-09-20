package com.omgservers.module.script.impl.event.runtime;

import com.omgservers.module.script.impl.event.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UpdateRuntimeLuaEvent extends LuaEvent {

    final Long time;

    public UpdateRuntimeLuaEvent(final Long time) {
        super("update_runtime");
        this.time = time;

        set("time", time);
    }
}
