package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.BroadcastMessageOutgoingCommandBodyModel;
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
    public OutgoingCommandModel map(final LuaTable luaCommand) {
        final var luaMessage = luaCommand.get("message").checktable();

        final var outgoingCommandBody = new BroadcastMessageOutgoingCommandBodyModel(luaMessage);
        final var outgoingCommand = new OutgoingCommandModel(OutgoingCommandQualifierEnum.BROADCAST_MESSAGE,
                outgoingCommandBody);
        return outgoingCommand;
    }
}
