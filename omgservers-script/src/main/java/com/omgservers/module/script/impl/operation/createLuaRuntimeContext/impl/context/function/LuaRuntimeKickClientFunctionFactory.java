package com.omgservers.module.script.impl.operation.createLuaRuntimeContext.impl.context.function;

import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaRuntimeKickClientFunctionFactory {

    final RuntimeModule runtimeModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    public LuaRuntimeKickClientFunction build(final Long runtimeId) {
        final var function = new LuaRuntimeKickClientFunction(
                runtimeModule,
                handleLuaCallOperation,
                runtimeId);
        return function;
    }
}
