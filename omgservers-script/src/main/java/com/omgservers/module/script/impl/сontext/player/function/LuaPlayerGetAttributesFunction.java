package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.time.Duration;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerGetAttributesFunction extends VarArgFunction {
    private static final long TIMEOUT = 10L;

    @ToString.Exclude
    final UserModule userModule;

    final Long userId;
    final Long playerId;

    @Override
    public Varargs invoke(Varargs args) {
        try {
            final var request = new GetPlayerAttributesRequest(userId, playerId);
            final var response = userModule.getPlayerService().getPlayerAttributes(request)
                    .await().atMost(Duration.ofSeconds(TIMEOUT));

            final var luaAttributes = new LuaTable();
            response.getAttributes().getAttributes()
                    .forEach(attribute -> {
                        final var name = attribute.getName();
                        final var value = switch (attribute.getType()) {
                            case LONG -> LuaValue.valueOf(Long.parseLong(attribute.getValue()));
                            case DOUBLE -> LuaValue.valueOf(Double.parseDouble(attribute.getValue()));
                            case STRING -> LuaValue.valueOf(attribute.getValue());
                            case BOOLEAN -> LuaValue.valueOf(Boolean.parseBoolean(attribute.getValue()));
                        };
                        luaAttributes.set(name, value);
                    });
            return luaAttributes;
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
