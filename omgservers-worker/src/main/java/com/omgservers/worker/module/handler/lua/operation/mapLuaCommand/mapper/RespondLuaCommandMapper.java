package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoRespondCommandBodyModel;
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
public class RespondLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.RESPOND;
    }

    @Override
    public DoCommandModel map(final LuaContext luaContext, LuaTable luaCommand) {
        final var clientId = Long.valueOf(luaCommand.get("client_id").checkjstring());
        final var luaMessage = luaCommand.get("message").checktable();

        final var doCommandBody = new DoRespondCommandBodyModel(clientId, luaMessage);
        final var doCommandModel = new DoCommandModel(DoCommandQualifierEnum.DO_RESPOND, doCommandBody);
        return doCommandModel;
    }
}
