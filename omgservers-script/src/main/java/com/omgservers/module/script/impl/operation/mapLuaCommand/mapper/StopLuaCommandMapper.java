package com.omgservers.module.script.impl.operation.mapLuaCommand.mapper;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.StopRequestedEventBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.script.impl.operation.mapLuaCommand.LuaCommandMapper;
import com.omgservers.module.system.factory.EventModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopLuaCommandMapper implements LuaCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.STOP;
    }

    @Override
    public EventModel map(final ScriptModel script, LuaTable luaCommand) {
        final var runtimeId = script.getRuntimeId();

        final var reason = luaCommand.get("reason").checkjstring();

        final var eventBody = new StopRequestedEventBodyModel(runtimeId, reason);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
