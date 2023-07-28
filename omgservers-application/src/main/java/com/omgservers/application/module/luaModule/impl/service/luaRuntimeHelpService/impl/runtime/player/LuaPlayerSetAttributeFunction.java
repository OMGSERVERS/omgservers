package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.SyncAttributeInternalRequest;
import com.omgservers.application.module.userModule.model.attribute.AttributeModelFactory;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerSetAttributeFunction extends TwoArgFunction {
    static private final long TIMEOUT = 1L;

    @ToString.Exclude
    final UserModule userModule;

    @ToString.Exclude
    final AttributeModelFactory attributeModelFactory;

    @ToString.Exclude
    final GenerateIdOperation generateIdOperation;

    final Long userId;
    final Long playerId;

    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        String name = arg1.tojstring();
        String value = arg2.tojstring();

        final var attribute = attributeModelFactory.create(playerId, name, value);
        final var syncAttributeServiceRequest = new SyncAttributeInternalRequest(userId, attribute);

        try {
            userModule.getAttributeInternalService().syncAttribute(TIMEOUT, syncAttributeServiceRequest);
            return LuaValue.NIL;
        } catch (Exception e) {
            final var error = e.getMessage();
            log.warn("Lua function failed, function={}, {}", this, error);
            return LuaString.valueOf(error);
        }
    }
}
