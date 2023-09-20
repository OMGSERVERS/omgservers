package com.omgservers.module.script.impl.сontext.runtime.function;

import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaRuntimeUnicastMessageFunctionFactory {

    final RuntimeModule runtimeModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    public LuaRuntimeUnicastMessageFunction build(final Long runtimeId) {
        final var function = new LuaRuntimeUnicastMessageFunction(
                runtimeModule,
                handleLuaCallOperation,
                runtimeId);
        return function;
    }
}
