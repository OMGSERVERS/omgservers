package com.omgservers.module.script.impl.operation.getLuaInstance;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.operation.createLuaGlobals.impl.LuaGlobals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

@Data
@Slf4j
@AllArgsConstructor
public class LuaInstance {

    final LuaGlobals luaGlobals;

    public synchronized LuaValue callScript(final LuaValue luaState, final LuaRequest luaRequest) {
        try {
            final var globals = luaGlobals.getGlobals();
            globals.set("request", luaRequest);
            globals.set("state", luaState);
            return globals.loadfile("main.lua").call();
        } catch (LuaError luaError) {
            log.warn("Lua instance failed, reason={}, luaRequest={}", luaError.getMessage(), luaRequest);
            throw new ServerSideBadRequestException("Lua error, " + luaError.getMessage(), luaError);
        }
    }
}
