package com.omgservers.worker.module.handler.lua.luaRequest.impl;

import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class InitRuntimeLuaRequest extends LuaRequest {

    public InitRuntimeLuaRequest(final LuaValue config) {
        super("init_runtime", true);

        set("config", config);
    }
}
