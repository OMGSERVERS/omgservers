package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoBroadcastMessageCommandBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BroadcastMessageLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.BROADCAST_MESSAGE;
    }

    @Override
    public DoCommandModel map(final LuaContext luaContext, final LuaTable luaCommand) {
        final var luaMessage = luaCommand.get("message").checktable();

        final var doCommandBody = new DoBroadcastMessageCommandBodyModel(luaMessage);
        final var doCommandModel = new DoCommandModel(DoCommandQualifierEnum.DO_BROADCAST_MESSAGE, doCommandBody);
        return doCommandModel;
    }
}
