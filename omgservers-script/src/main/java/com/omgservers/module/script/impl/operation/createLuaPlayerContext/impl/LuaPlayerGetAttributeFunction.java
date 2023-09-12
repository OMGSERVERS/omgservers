package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl;

import com.omgservers.module.user.UserModule;
import com.omgservers.dto.user.GetAttributeRequest;
import io.smallrye.mutiny.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerGetAttributeFunction extends VarArgFunction {
    private static final long TIMEOUT = 10L;

    @ToString.Exclude
    final UserModule userModule;

    final Long userId;
    final Long playerId;

    @Override
    public Varargs invoke(Varargs args) {
        final var name = args.checkjstring(1);
        final var request = new GetAttributeRequest(userId, playerId, name);

        try {
            final var response = userModule.getAttributeService().getAttribute(TIMEOUT, request);
            final var attribute = response.getAttribute();
            final var value = LuaValue.valueOf(attribute.getValue());
            return value;
        } catch (TimeoutException e) {
            log.error("Lua call failed due to timeout, function={}", this);
            return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("timeout"));
        } catch (Exception e) {
            final var error = e.getMessage();
            log.warn("Lua call failed due to exception, function={}, {}", this, error, e);
            return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("failed"));
        }
    }
}
