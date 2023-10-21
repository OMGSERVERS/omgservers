package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StopRuntimeLuaRequest extends LuaRequest {

    public StopRuntimeLuaRequest() {
        super("stop_runtime");
    }
}
