package com.omgservers.module.handler;

import com.omgservers.module.handler.impl.operation.createLuaRuntime.impl.LuaRuntime;
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
public class TestLuaRuntime {

    final CreateServerGlobalsOperation createServerGlobalsOperation;

    public LuaRuntime createTestRuntimeForScript(String script) {
        return createTestRuntimeForScript(script, new HashMap<>());
    }

    public LuaRuntime createTestRuntimeForScript(String script, Map<String, LibFunction> libFunctions) {
        final var globals = createServerGlobalsOperation.createServerGlobals();
        globals.finder = filename -> new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8));
        final var luaRuntime = new LuaRuntime(globals);
        libFunctions.entrySet().stream()
                .forEach(entry -> {
                    final var id = entry.getKey();
                    final var func = entry.getValue();
                    luaRuntime.getGlobals().set(id, func);
                });
        luaRuntime.getGlobals().get("dofile").call(LuaValue.valueOf("main.lua"));
        return luaRuntime;
    }
}
