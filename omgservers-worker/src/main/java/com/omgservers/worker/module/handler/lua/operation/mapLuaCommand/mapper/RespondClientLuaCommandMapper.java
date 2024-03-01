package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.RespondClientOutgoingCommandBodyModel;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RespondClientLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.RESPOND_CLIENT;
    }

    @Override
    public OutgoingCommandModel map(final LuaTable luaCommand) {
        final var clientId = Long.valueOf(luaCommand.get("client_id").checkjstring());
        final var luaMessage = luaCommand.get("message").checktable();

        final var outgoingCommandBody = new RespondClientOutgoingCommandBodyModel(clientId, luaMessage);
        final var outgoingCommandModel = new OutgoingCommandModel(OutgoingCommandQualifierEnum.RESPOND_CLIENT,
                outgoingCommandBody);
        return outgoingCommandModel;
    }
}
