package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
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

    MapLuaCommandOperationImpl(Instance<LuaCommandMapper> luaCommandMappersBeans) {
        this.luaCommandMappers = new ConcurrentHashMap<>();
        luaCommandMappersBeans.stream().forEach(luaCommandMapper -> {
            final var qualifier = luaCommandMapper.getQualifier();
            if (luaCommandMappers.put(qualifier, luaCommandMapper) != null) {
                log.error("Multiple lua command mappers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public DoCommandModel mapLuaCommand(LuaContext luaContext, final LuaTable luaCommand) {
        try {
            final var luaQualifier = luaCommand.get("qualifier").checkjstring();
            final var qualifier = fromString(luaQualifier);
            final var mapper = luaCommandMappers.get(qualifier);
            return mapper.map(luaContext, luaCommand);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static LuaCommandQualifierEnum fromString(String qualifier) {
        for (final var q : LuaCommandQualifierEnum.values()) {
            if (q.getQualifier().equals(qualifier)) {
                return q;
            }
        }
        throw new IllegalArgumentException("Unknown lua command qualifier, " +
                "qualifier=" + qualifier);
    }
}
