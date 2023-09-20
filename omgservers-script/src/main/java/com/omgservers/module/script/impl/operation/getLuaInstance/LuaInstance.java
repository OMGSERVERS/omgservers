package com.omgservers.module.script.impl.operation.getLuaInstance;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.module.script.impl.event.LuaEvent;
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

    public synchronized void callScript(final LuaValue luaState, final LuaEvent luaEvent) {
        try {
            final var globals = luaGlobals.getGlobals();
            luaContext.set("state", luaState);
            luaContext.set("event", luaEvent);
            globals.set("context", luaContext);
            globals.loadfile("main.lua").call();
        } catch (LuaError luaError) {
            log.warn("Lua instance failed, reason={}, luaEvent={}", luaError.getMessage(), luaEvent);
            throw new ServerSideBadRequestException("Lua error, " + luaError.getMessage(), luaError);
        }
    }
}
