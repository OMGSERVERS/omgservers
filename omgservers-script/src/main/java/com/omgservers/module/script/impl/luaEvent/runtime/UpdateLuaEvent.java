package com.omgservers.module.script.impl.luaEvent.runtime;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UpdateLuaEvent extends LuaEvent {

    final Long time;

    public UpdateLuaEvent(final Long time) {
        super("update");
        this.time = time;

        set("time", time);
    }
}
