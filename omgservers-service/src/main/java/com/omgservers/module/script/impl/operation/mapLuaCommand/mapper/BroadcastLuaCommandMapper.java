package com.omgservers.module.script.impl.operation.mapLuaCommand.mapper;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.BroadcastRequestedEventBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
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
public class BroadcastLuaCommandMapper implements LuaCommandMapper {

    final EventModelFactory eventModelFactory;

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.BROADCAST;
    }

    @Override
    public EventModel map(final ScriptModel script, LuaTable luaCommand) {
        final var runtimeId = script.getRuntimeId();

        final var luaMessage = luaCommand.get("message").checktable();

        final var eventBody = new BroadcastRequestedEventBodyModel(runtimeId, luaMessage);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
    }
}
