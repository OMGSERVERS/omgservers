package com.omgservers.worker.module.handler.lua.operation.createLuaInstance;

import com.omgservers.worker.module.handler.lua.luaCommand.LuaCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

@Slf4j
@AllArgsConstructor
public class LuaInstance {

    final Globals globals;
    final LuaValue chunk;

    public LuaInstance(final Globals globals,
                       final String filename) {
        this.globals = globals;
        chunk = globals.loadfile(filename);
    }

    public synchronized LuaValue call(final LuaCommand luaCommand) {
        try {
            return chunk.call(luaCommand);
        } catch (LuaError luaError) {
            log.warn("Lua instance failed, reason={}, luaCommand={}", luaError.getMessage(), luaCommand);
            throw new IllegalArgumentException("Lua error, " + luaError.getMessage(), luaError);
        }
    }
}
