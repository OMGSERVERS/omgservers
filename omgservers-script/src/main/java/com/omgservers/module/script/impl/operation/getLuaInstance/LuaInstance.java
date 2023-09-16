package com.omgservers.module.script.impl.operation.getLuaInstance;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import com.omgservers.module.script.impl.operation.createLuaGlobals.impl.LuaGlobals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

@Data
@Slf4j
@AllArgsConstructor
public class LuaInstance {

    final LuaGlobals luaGlobals;
    final LuaTable luaContext;
    final LuaValue luaSelf;

    public synchronized boolean handle(final LuaEvent luaEvent) {
        final var eventId = luaEvent.getId();
        final var closure = luaGlobals.getGlobals().get(eventId);
        if (closure.isnil()) {
            log.warn("Lua closure was not found, id={}", eventId);
            return false;
        } else {
            try {
                closure.call(luaSelf, luaEvent, luaContext);
                return true;
            } catch (LuaError luaError) {
                log.warn("Lua closure call failed, reason={}, luaEvent={}", luaError.getMessage(), luaEvent);
                throw new ServerSideBadRequestException("Lua error, id=" + eventId + ", " + luaError.getMessage());
            }
        }
    }
}
