package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;

public interface RuntimeCommandMapper {

    RuntimeCommandQualifierEnum getQualifier();

    LuaCommand map(RuntimeCommandModel runtimeCommand);
}
