package com.omgservers.module.script.impl.сontext.player.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerSetObjectFunction extends VarArgFunction {

    @ToString.Exclude
    final UserModule userModule;

    @ToString.Exclude
    final HandleLuaCallOperation handleLuaCallOperation;
    @ToString.Exclude
    final ObjectMapper objectMapper;

    final Long userId;
    final Long playerId;

    @Override
    public Varargs invoke(Varargs args) {
        return handleLuaCallOperation.handleLuaCall(() -> {
            final var luaObject = args.arg(1).checktable();

            final var request = new UpdatePlayerObjectRequest(userId, playerId, luaObject);
            return userModule.getPlayerService().updatePlayerObject(request)
                    .replaceWith(LuaValue.NIL);
        });
    }
}
