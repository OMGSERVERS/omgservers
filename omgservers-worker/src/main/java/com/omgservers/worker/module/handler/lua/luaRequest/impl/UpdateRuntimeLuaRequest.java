package com.omgservers.worker.module.handler.lua.luaRequest.impl;

import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateRuntimeLuaRequest extends LuaRequest {

    final Long time;

    public UpdateRuntimeLuaRequest(final Long time) {
        super("update_runtime", false);
        this.time = time;

        set("time", time);
    }
}
