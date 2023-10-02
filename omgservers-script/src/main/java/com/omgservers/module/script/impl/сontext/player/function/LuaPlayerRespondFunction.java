package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.dto.user.RespondClientRequest;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

@Slf4j
@Builder
@AllArgsConstructor
public class LuaPlayerRespondFunction extends VarArgFunction {
    final UserModule userModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    final MessageModelFactory messageModelFactory;

    final Long userId;
    final Long clientId;

    @Override
    public Varargs invoke(Varargs args) {
        return handleLuaCallOperation.handleLuaCall(() -> {
            final var event = args.arg(1).checkjstring();

            final var body = new ServerMessageBodyModel(event);
            final var message = messageModelFactory.create(MessageQualifierEnum.SERVER_MESSAGE, body);
            final var request = new RespondClientRequest(userId, clientId, message);
            return userModule.getUserService().respondClient(request)
                    .replaceWith(LuaValue.NIL);
        });
    }
}
