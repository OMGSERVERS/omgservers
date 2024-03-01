package com.omgservers.worker.module.handler.lua.component.luaInstance;

import com.omgservers.worker.exception.WorkerStartUpException;
import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

@Slf4j
@AllArgsConstructor
public class LuaInstance {

    final Globals globals;
    final LuaValue luaChunk;
    final LuaValue luaSelf;

    public LuaInstance(final Globals globals,
                       final String filename) {
        this.globals = globals;
        luaChunk = globals.loadfile(filename);
        luaSelf = LuaTable.tableOf();
    }

    public synchronized void start() {
        luaChunk.call();
        // Call to check that lua handler is here
        final var handler = getHandler();
        if (handler == null || handler.isnil()) {
            throw new WorkerStartUpException("Handler handle_command was not found");
        }
    }

    public synchronized LuaValue call(final LuaCommand luaCommand) {
        final var handler = getHandler();

        try {
            return handler.call(luaSelf, luaCommand);
        } catch (LuaError luaError) {
            log.warn("Lua handler failed, reason={}, luaCommand={}", luaError.getMessage(), luaCommand);
            throw new IllegalArgumentException("Lua error, " + luaError.getMessage(), luaError);
        }
    }

    LuaValue getHandler() {
        final var handler = globals.get("handle_command");
        return handler;
    }
}
