package com.omgservers.module.script.impl.operation.createLuaGlobals.impl;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;

@Slf4j
@Getter
@ToString
@AllArgsConstructor
public class LuaGlobals {

    final Long tenantId;
    final Long versionId;

    @ToString.Exclude
    final Globals globals;

    public void handleEvent(LuaEvent luaEvent, LuaTable luaContext) {
        final var eventId = luaEvent.getId();
        final var closure = globals.get(eventId);
        if (closure.isnil()) {
            log.warn("Closure was not found, id={}", eventId);
        } else {
            try {
                closure.call(luaEvent, luaContext);
            } catch (LuaError luaError) {
                log.warn("Closure call failed, id={}, reason={}", eventId, luaError.getMessage());
                throw new ServerSideBadRequestException("Lua error, id=" + eventId + ", " + luaError.getMessage());
            }
        }
    }
}
