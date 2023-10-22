package com.omgservers.module.script.impl.operation.mapLuaCommand;

import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.script.ScriptModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class MapLuaCommandOperationImpl implements MapLuaCommandOperation {

    final Map<LuaCommandQualifierEnum, LuaCommandMapper> luaCommandMappers;

    MapLuaCommandOperationImpl(Instance<LuaCommandMapper> scriptEventMappersBeans) {
        this.luaCommandMappers = new ConcurrentHashMap<>();
        scriptEventMappersBeans.stream().forEach(luaCommandMapper -> {
            final var qualifier = luaCommandMapper.getQualifier();
            if (luaCommandMappers.put(qualifier, luaCommandMapper) != null) {
                log.error("Multiple lua command mappers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public EventModel mapLuaCommand(ScriptModel script, final LuaTable luaCommand) {
        try {
            final var luaQualifier = luaCommand.get("qualifier").checkjstring();
            final var qualifier = LuaCommandQualifierEnum.fromString(luaQualifier);
            final var mapper = luaCommandMappers.get(qualifier);
            return mapper.map(script, luaCommand);
        } catch (Exception e) {
            throw new ServerSideConflictException(e.getMessage(), e);
        }
    }
}
