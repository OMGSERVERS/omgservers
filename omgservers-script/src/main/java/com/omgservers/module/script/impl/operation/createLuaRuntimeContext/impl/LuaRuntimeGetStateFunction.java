package com.omgservers.module.script.impl.operation.createLuaRuntimeContext.impl;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaRuntimeGetStateFunction extends ZeroArgFunction {

    @Override
    public LuaValue call() {
        return null;
    }
}
