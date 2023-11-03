package com.omgservers.worker.module.handler.lua.operation.createLuaInstance;

import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

@Slf4j
@AllArgsConstructor
public class LuaInstance {

    final LuaValue luaState;
    final Globals globals;
    final LuaValue chunk;

    public LuaInstance(final LuaValue luaState,
                       final Globals globals,
                       final String filename) {
        this.luaState = luaState;
        this.globals = globals;
        chunk = globals.loadfile(filename);
    }

    public synchronized LuaValue call(final LuaRequest luaRequest) {
        try {
            globals.set("request", luaRequest);
            globals.set("state", luaState);
            return chunk.call();
        } catch (LuaError luaError) {
            log.warn("Lua instance failed, reason={}, luaRequest={}", luaError.getMessage(), luaRequest);
            throw new IllegalArgumentException("Lua error, " + luaError.getMessage(), luaError);
        }
    }
}
