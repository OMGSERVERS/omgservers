package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl;

import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.EventMessageBodyModel;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.UUID;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerRespondFunction extends OneArgFunction {
    private static final long TIMEOUT = 10L;

    @ToString.Exclude
    final UserModule userModule;
    final Long userId;
    final Long clientId;

    @Override
    public LuaValue call(LuaValue arg) {
        final var event = arg.tojstring();
        final var body = new EventMessageBodyModel(event);
        final var message = new MessageModel(UUID.randomUUID().toString(), MessageQualifierEnum.EVENT_MESSAGE, body);
        final var request = new RespondClientRequest(userId, clientId, message);

        try {
            userModule.getUserService().respondClient(TIMEOUT, request);
            return LuaValue.NIL;
        } catch (TimeoutException e) {
            log.error("Lua call failed due to timeout, function={}", this);
            return LuaString.valueOf("timeout");
        } catch (Exception e) {
            final var error = e.getMessage();
            log.warn("Lua call failed due to exception, function={}, {}", this, error, e);
            return LuaString.valueOf("failed");
        }
    }
}
