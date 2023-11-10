package com.omgservers.worker.module.handler.lua.operation.createLuaContext;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.workerContext.WorkerContextModel;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import com.omgservers.worker.module.handler.lua.operation.coerceJavaObject.CoerceJavaObjectOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateLuaContextOperationImpl implements CreateLuaContextOperation {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public LuaContext createLuaContext(WorkerContextModel workerContext) {
        final var lauAttributesByUserId = new HashMap<Long, LuaValue>();
        final var luaProfileByUserId = new HashMap<Long, LuaValue>();

        workerContext.getPlayers().forEach(player -> {
            final var userId = player.getUserId();
            lauAttributesByUserId.put(userId, parseAttributes(player.getAttributes()));
            luaProfileByUserId.put(userId, coerceJavaObjectOperation.coerceJavaObject(player.getProfile()));
        });

        final var luaContext = new LuaContext(lauAttributesByUserId, luaProfileByUserId);
        return luaContext;
    }

    LuaTable parseAttributes(PlayerAttributesModel attributes) {
        final var luaAttributes = new LuaTable();
        attributes.getAttributes().forEach(attribute -> {
            final var name = attribute.getName();
            final var value = attribute.getValue();
            switch (attribute.getType()) {
                case LONG -> luaAttributes.set(name, Long.parseLong(value));
                case DOUBLE -> luaAttributes.set(name, Double.parseDouble(value));
                case STRING -> luaAttributes.set(name, value);
                case BOOLEAN -> luaAttributes.set(name, LuaBoolean.valueOf(Boolean.parseBoolean(value)));
            }
        });

        return luaAttributes;
    }
}
