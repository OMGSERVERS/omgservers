package com.omgservers.module.script.impl.luaContext.runtime.function;

import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;

@Slf4j
@AllArgsConstructor
public class LuaRuntimeMulticastMessageFunction extends VarArgFunction {
    final RuntimeModule runtimeModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    final Long runtimeId;

    @Override
    public Varargs invoke(Varargs args) {
        return handleLuaCallOperation.handleLuaCall(() -> {
            final var t = args.arg(1).checktable();
            final var clientIds = new ArrayList<Long>();
            var k = LuaValue.NIL;
            while (true) {
                final var n = t.next(k);
                k = n.arg1();
                if (k.isnil()) {
                    break;
                }
                final var v = n.arg(2);
                clientIds.add(v.checklong());
            }

            final var message = args.arg(2).checkjstring();

            final var doMulticastMessageRequest = new DoMulticastMessageRequest(runtimeId, clientIds, message);
            return runtimeModule.getDoService().doMulticastMessage(doMulticastMessageRequest)
                    .replaceWith(LuaValue.NIL);
        });
    }
}
