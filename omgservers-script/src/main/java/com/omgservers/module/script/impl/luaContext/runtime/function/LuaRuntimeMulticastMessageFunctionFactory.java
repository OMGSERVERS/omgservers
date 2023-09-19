package com.omgservers.module.script.impl.luaContext.runtime.function;

import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaRuntimeMulticastMessageFunctionFactory {

    final RuntimeModule runtimeModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    public LuaRuntimeMulticastMessageFunction build(final Long runtimeId) {
        final var function = new LuaRuntimeMulticastMessageFunction(
                runtimeModule,
                handleLuaCallOperation,
                runtimeId);
        return function;
    }
}
