package com.omgservers.worker.module.handler.lua.operation.createLuaInstance;

import com.omgservers.model.version.VersionSourceCodeModel;

public interface CreateLuaInstanceOperation {
    LuaInstance createLuaInstance(VersionSourceCodeModel versionSourceCode);
}
