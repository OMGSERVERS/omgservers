package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.SetProfileOutgoingCommandBodyModel;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetProfileLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.SET_PROFILE;
    }

    @Override
    public OutgoingCommandModel map(LuaTable luaCommand) {
        final var clientId = Long.valueOf(luaCommand.get("client_id").checkjstring());

        final var luaProfile = luaCommand.get("profile").checktable();

        final var outgoingCommandBody = new SetProfileOutgoingCommandBodyModel(clientId, luaProfile);
        final var outgoingCommand = new OutgoingCommandModel(OutgoingCommandQualifierEnum.SET_PROFILE,
                outgoingCommandBody);
        return outgoingCommand;
    }
}
