package com.omgservers.worker.module.handler.lua.operation.createLuaContext;

import com.omgservers.model.workerContext.WorkerContextModel;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;

public interface CreateLuaContextOperation {
    LuaContext createLuaContext(WorkerContextModel workerContext);
}
