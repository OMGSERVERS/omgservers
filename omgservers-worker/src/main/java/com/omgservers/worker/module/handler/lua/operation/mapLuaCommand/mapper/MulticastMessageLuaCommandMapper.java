package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.MulticastMessageOutgoingCommandBodyModel;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastMessageLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.MULTICAST_MESSAGE;
    }

    @Override
    public OutgoingCommandModel map(final LuaTable luaCommand) {
        final var luaClients = luaCommand.get("clients").checktable();
        final var clients = new ArrayList<Long>();
        for (int i = 1; i <= luaClients.length(); i++) {
            clients.add(Long.valueOf(luaClients.get(i).checkjstring()));
        }
        final var luaMessage = luaCommand.get("message").checktable();

        final var outgoingCommandBody = new MulticastMessageOutgoingCommandBodyModel(clients, luaMessage);
        final var outgoingCommandModel = new OutgoingCommandModel(OutgoingCommandQualifierEnum.MULTICAST_MESSAGE,
                outgoingCommandBody);
        return outgoingCommandModel;
    }
}
