package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.userHelpService.request.RespondClientHelpRequest;
import com.omgservers.application.module.gatewayModule.model.message.MessageModel;
import com.omgservers.application.module.gatewayModule.model.message.MessageQualifierEnum;
import com.omgservers.application.module.gatewayModule.model.message.body.EventMessageBodyModel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerRespondFunction extends OneArgFunction {
    static private final long TIMEOUT = 1L;

    @ToString.Exclude
    final UserModule userModule;
    final Long userId;
    final Long clientId;

    @Override
    public LuaValue call(LuaValue arg) {
        final var event = arg.checkjstring();
        final var body = new EventMessageBodyModel(event);
        final var message = MessageModel.create(MessageQualifierEnum.EVENT_MESSAGE, body);
        final var request = new RespondClientHelpRequest(userId, clientId, message);

        try {
            userModule.getUserHelpService().respondClient(TIMEOUT, request);
            return LuaValue.NIL;
        } catch (Exception e) {
            final var error = e.getMessage();
            log.warn("Lua function failed, function={}, {}", this, error);
            return LuaString.valueOf(error);
        }
    }
}
