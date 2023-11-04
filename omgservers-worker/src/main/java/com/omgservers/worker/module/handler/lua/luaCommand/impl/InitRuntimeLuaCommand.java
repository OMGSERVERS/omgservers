package com.omgservers.worker.module.handler.lua.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class InitRuntimeLuaCommand extends LuaCommand {

    public InitRuntimeLuaCommand(final LuaValue config) {
        super("init_runtime", true);

        set("config", config);
    }
}
