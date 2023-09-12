package com.omgservers.module.script.impl.operation.getLuaInstance;

import com.omgservers.model.script.ScriptModel;
import io.smallrye.mutiny.Uni;

public interface GetLuaInstanceOperation {
    Uni<LuaInstance> getLuaInstance(ScriptModel script);
}
