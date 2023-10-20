package com.omgservers.module.script.impl.luaRequest;

import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
@ToString
public class InitRuntimeLuaRequest extends LuaRequest {

    final LuaValue config;

    public InitRuntimeLuaRequest(final LuaValue config) {
        super("init_runtime");
        this.config = config;

        set("config", config);
    }
}
