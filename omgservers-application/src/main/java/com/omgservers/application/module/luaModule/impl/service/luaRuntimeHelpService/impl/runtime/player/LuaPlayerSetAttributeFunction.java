package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.model.attribute.AttributeModel;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.SyncAttributeInternalRequest;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.UUID;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerSetAttributeFunction extends TwoArgFunction {
    static private final long TIMEOUT = 1L;

    @ToString.Exclude
    final UserModule userModule;

    final UUID user;
    final UUID player;

    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        String name = arg1.checkjstring();
        String value = arg2.checkjstring();

        final var attribute = AttributeModel.create(player, name, value);
        final var syncAttributeServiceRequest = new SyncAttributeInternalRequest(user, attribute);

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
