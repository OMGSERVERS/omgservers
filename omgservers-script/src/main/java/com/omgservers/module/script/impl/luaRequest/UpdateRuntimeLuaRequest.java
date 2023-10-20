package com.omgservers.module.script.impl.luaRequest;

import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UpdateRuntimeLuaRequest extends LuaRequest {

    final Long time;

    public UpdateRuntimeLuaRequest(final Long time) {
        super("update_runtime");
        this.time = time;

        set("time", time);
    }
}
