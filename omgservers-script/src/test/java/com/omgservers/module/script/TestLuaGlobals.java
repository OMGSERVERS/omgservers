package com.omgservers.module.script;

import com.omgservers.module.script.impl.operation.createLuaGlobals.impl.LuaGlobals;
import com.omgservers.operation.createServerGlobals.CreateServerGlobalsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.LibFunction;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@AllArgsConstructor
public class TestLuaGlobals {

    final CreateServerGlobalsOperation createServerGlobalsOperation;

    public LuaGlobals createTestGlobalsForScript(String script) {
        return createTestGlobalsForScript(script, new HashMap<>());
    }

    public LuaGlobals createTestGlobalsForScript(String script, Map<String, LibFunction> libFunctions) {
        final var globals = createServerGlobalsOperation.createServerGlobals();
        globals.finder = filename -> new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8));
        final var luaGlobals = new LuaGlobals(0L, 0L, globals);
        libFunctions.entrySet().stream()
                .forEach(entry -> {
                    final var id = entry.getKey();
                    final var func = entry.getValue();
                    luaGlobals.getGlobals().set(id, func);
                });
        luaGlobals.getGlobals().get("dofile").call(LuaValue.valueOf("main.lua"));
        return luaGlobals;
    }
}
