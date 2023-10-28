package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UpdateRuntimeLuaRequest extends LuaRequest {

    final Long time;

    public UpdateRuntimeLuaRequest(final Long time) {
        super("update_runtime", false);
        this.time = time;

        set("time", time);
    }
}
