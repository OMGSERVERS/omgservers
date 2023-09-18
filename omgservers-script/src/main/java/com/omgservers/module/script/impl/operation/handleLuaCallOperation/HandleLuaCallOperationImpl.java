package com.omgservers.module.script.impl.operation.handleLuaCallOperation;

import io.smallrye.mutiny.TimeoutException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleLuaCallOperationImpl implements HandleLuaCallOperation {
    private static final long TIMEOUT = 1L;

    @Override
    public Varargs handleLuaCall(final Supplier<Uni<Varargs>> supplier) {
        try {
            return supplier.get()
                    .await().atMost(Duration.ofSeconds(TIMEOUT));
        } catch (TimeoutException e) {
            log.error("Lua call failed due to timeout, {}", e.getMessage());
            return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("timeout"));
        } catch (Exception e) {
            log.warn("Lua call failed due to exception, {}", e.getMessage());
            return LuaValue.varargsOf(LuaValue.NIL, LuaString.valueOf("failed"));
        }
    }
}
