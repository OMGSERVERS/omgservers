package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event.LuaEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;

@Data
@Slf4j
@AllArgsConstructor
public class LuaRuntime {

    final Globals globals;

    public void handleEvent(LuaEvent luaEvent, LuaTable context) {
        final var eventId = luaEvent.getId();
        final var closure = globals.get(eventId);
        if (closure.isnil()) {
            log.info("Closure was not found, id={}", eventId);
        } else {
            try {
                closure.call(luaEvent, context);
            } catch (LuaError luaError) {
                log.warn("Closure call failed, id={}, reason={}", eventId, luaError.getMessage());
            }
        }
    }
}
