package com.omgservers.worker.module.handler.lua.operation.createLuaInstance;

import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.worker.module.handler.lua.component.luaInstance.LuaInstance;

public interface CreateLuaInstanceOperation {
    LuaInstance createLuaInstance(VersionSourceCodeModel versionSourceCode);
}
