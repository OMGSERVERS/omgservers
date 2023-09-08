package com.omgservers.module.context.impl.operation.createLuaInstance.impl;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import com.omgservers.module.context.impl.operation.createLuaGlobals.impl.LuaGlobals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;

@Data
@Slf4j
@AllArgsConstructor
public class LuaInstance {

    final LuaGlobals luaGlobals;
    final LuaTable luaContext;

    public synchronized boolean handleEvent(final LuaEvent luaEvent) {
        final var eventId = luaEvent.getId();
        final var closure = luaGlobals.getGlobals().get(eventId);
        if (closure.isnil()) {
            log.info("Closure was not found, id={}", eventId);
            return false;
        } else {
            try {
                closure.call(luaEvent, luaContext);
                return true;
            } catch (LuaError luaError) {
                log.warn("Closure call failed, id={}, reason={}", eventId, luaError.getMessage());
                throw new ServerSideBadRequestException("Lua error, id=" + eventId + ", " + luaError.getMessage());
            }
        }
    }
}
