package com.omgservers.module.context.impl.operation.createLuaInstance.impl;

import com.omgservers.module.context.impl.operation.createLuaGlobals.impl.LuaGlobals;
import com.omgservers.module.context.impl.operation.createLuaInstance.CreateLuaInstanceOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaInstanceOperationImpl implements CreateLuaInstanceOperation {

    @Override
    public Uni<LuaInstance> createLuaInstance(LuaGlobals luaGlobals, LuaTable luaContext) {
        return Uni.createFrom().item(new LuaInstance(luaGlobals, luaContext))
                .invoke(luaInstance -> log.info("Lua instance was created, luaInstance={}", luaInstance));
    }
}
