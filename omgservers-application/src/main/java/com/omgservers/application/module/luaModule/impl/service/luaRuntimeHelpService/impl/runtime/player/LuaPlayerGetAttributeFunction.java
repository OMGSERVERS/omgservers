package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetAttributeInternalRequest;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.UUID;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerGetAttributeFunction extends VarArgFunction {
    static private final long TIMEOUT = 1L;

    @ToString.Exclude
    final UserModule userModule;

    final UUID user;
    final UUID player;

    @Override
    public Varargs invoke(Varargs args) {
        final var name = args.checkjstring(1);
        final var request = new GetAttributeInternalRequest(user, player, name);

        try {
            final var response = userModule.getAttributeInternalService().getAttribute(TIMEOUT, request);
            final var attribute = response.getAttribute();
            final var value = LuaValue.valueOf(attribute.getValue());
            return value;
        } catch (Exception e) {
            final var error = e.getMessage();
            log.warn("Lua call failed, function={}, {}", this, error);
            return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf(error));
        }
    }
}
