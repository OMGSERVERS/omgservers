package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerGetObjectFunction extends VarArgFunction {

    @ToString.Exclude
    final UserModule userModule;

    @ToString.Exclude
    final CoerceJavaObjectOperation coerceJavaObjectOperation;
    @ToString.Exclude
    final HandleLuaCallOperation handleLuaCallOperation;

    final Long userId;
    final Long playerId;

    @Override
    public Varargs invoke(Varargs args) {
        return handleLuaCallOperation.handleLuaCall(() -> {
            final var request = new GetPlayerObjectRequest(userId, playerId);
            return userModule.getPlayerService().getPlayerObject(request)
                    .map(GetPlayerObjectResponse::getObject)
                    .map(coerceJavaObjectOperation::coerceJavaObject);
        });
    }
}
