package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl.context.function;

import com.omgservers.dto.user.SyncAttributeRequest;
import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.factory.AttributeModelFactory;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.TimeoutException;
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
    private static final long TIMEOUT = 10L;

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

        final var attribute = attributeModelFactory.create(userId, playerId, name, value);
        final var syncAttributeServiceRequest = new SyncAttributeRequest(userId, attribute);

        try {
            userModule.getAttributeService().syncAttribute(TIMEOUT, syncAttributeServiceRequest);
            return LuaValue.NIL;
        } catch (TimeoutException e) {
            log.error("Lua call failed due to timeout, function={}", this);
            return LuaString.valueOf("timeout");
        } catch (Exception e) {
            final var error = e.getMessage();
            log.warn("Lua call failed due to exception, function={}, {}", this, error);
            return LuaString.valueOf("failed");
        }
    }
}