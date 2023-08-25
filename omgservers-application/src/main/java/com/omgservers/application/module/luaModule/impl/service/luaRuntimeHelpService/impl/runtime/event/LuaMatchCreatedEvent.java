package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LuaMatchCreatedEvent extends LuaEvent {

    final String mode;

    public LuaMatchCreatedEvent(String mode) {
        super("match_created");
        this.mode = mode;
        set("mode", mode);
    }
}
