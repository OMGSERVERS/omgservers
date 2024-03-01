package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.StopMatchmakingOutgoingCommandBodyModel;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopMatchmakingLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.STOP_MATCHMAKING;
    }

    @Override
    public OutgoingCommandModel map(LuaTable luaCommand) {
        final var reason = luaCommand.get("reason").checkjstring();

        final var outgoingCommandBody = new StopMatchmakingOutgoingCommandBodyModel(reason);
        final var outgoingCommand = new OutgoingCommandModel(OutgoingCommandQualifierEnum.STOP_MATCHMAKING,
                outgoingCommandBody);
        return outgoingCommand;
    }
}
