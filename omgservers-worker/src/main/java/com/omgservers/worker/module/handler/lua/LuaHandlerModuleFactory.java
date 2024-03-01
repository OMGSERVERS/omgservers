package com.omgservers.worker.module.handler.lua;

import com.omgservers.model.version.VersionModel;
import com.omgservers.worker.module.handler.HandlerModule;
import com.omgservers.worker.module.handler.lua.operation.createLuaInstance.CreateLuaInstanceOperation;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.MapLuaCommandOperation;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.MapRuntimeCommandOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class LuaHandlerModuleFactory {

    final MapRuntimeCommandOperation mapRuntimeCommandOperation;
    final CreateLuaInstanceOperation createLuaInstanceOperation;
    final MapLuaCommandOperation mapLuaCommandOperation;

    public HandlerModule build(VersionModel version) {
        final var luaInstance = createLuaInstanceOperation.createLuaInstance(version.getSourceCode());
        final var module = new LuaHandlerModuleImpl(mapRuntimeCommandOperation,
                mapLuaCommandOperation,
                luaInstance);
        return module;
    }
}
