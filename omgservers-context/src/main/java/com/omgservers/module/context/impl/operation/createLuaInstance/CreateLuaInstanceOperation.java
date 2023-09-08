package com.omgservers.module.context.impl.operation.createLuaInstance;

import com.omgservers.module.context.impl.operation.createLuaGlobals.impl.LuaGlobals;
import com.omgservers.module.context.impl.operation.createLuaInstance.impl.LuaInstance;
import io.smallrye.mutiny.Uni;
import org.luaj.vm2.LuaTable;

import java.time.Duration;

public interface CreateLuaInstanceOperation {
    Uni<LuaInstance> createLuaInstance(LuaGlobals luaGlobals, LuaTable luaContext);

    default LuaInstance createLuaInstance(long timeout, LuaGlobals luaGlobals, LuaTable luaContext) {
        return createLuaInstance(luaGlobals, luaContext)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
