package com.omgservers.module.script.impl.operation.createLuaRuntimeContext.impl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaRuntimeGetStateFunctionFactory {

    public LuaRuntimeGetStateFunction build() {
        final var function = new LuaRuntimeGetStateFunction();
        return function;
    }
}
