package com.omgservers.module.script.impl.luaEvent.runtime;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateLuaEvent extends LuaEvent {

    final Long step;

    public UpdateLuaEvent(final Long step) {
        super("update");
        this.step = step;

        set("step", step);
    }
}
