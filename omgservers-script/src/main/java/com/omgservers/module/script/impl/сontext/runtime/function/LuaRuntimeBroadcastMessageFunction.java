package com.omgservers.module.script.impl.сontext.runtime.function;

import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

@Slf4j
@AllArgsConstructor
public class LuaRuntimeBroadcastMessageFunction extends VarArgFunction {
    final RuntimeModule runtimeModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    final Long runtimeId;

    @Override
    public Varargs invoke(Varargs args) {
        return handleLuaCallOperation.handleLuaCall(() -> {
            final var luaMessage = args.arg(1).checktable();

            final var doBroadcastMessageRequest = new DoBroadcastMessageRequest(runtimeId, luaMessage);
            return runtimeModule.getDoService().doBroadcastMessage(doBroadcastMessageRequest)
                    .replaceWith(LuaValue.NIL);
        });
    }
}
