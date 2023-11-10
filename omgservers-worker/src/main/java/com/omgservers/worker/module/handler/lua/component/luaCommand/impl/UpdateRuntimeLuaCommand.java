package com.omgservers.worker.module.handler.lua.component.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateRuntimeLuaCommand extends LuaCommand {

    final Long time;

    public UpdateRuntimeLuaCommand(final Long time) {
        super("update_runtime", false);
        this.time = time;

        set("time", time);
    }
}
