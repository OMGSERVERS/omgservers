package com.omgservers.module.script.impl.сontext.runtime.function;

import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class LuaRuntimeMulticastMessageFunction extends VarArgFunction {
    final RuntimeModule runtimeModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    final Long runtimeId;

    @Override
    public Varargs invoke(Varargs args) {
        return handleLuaCallOperation.handleLuaCall(() -> {
            final var luaRecipients = args.arg(1).checktable();
            final var recipients = parseRecipients(luaRecipients);

            final var message = args.arg(2).checkjstring();

            final var doMulticastMessageRequest = new DoMulticastMessageRequest(runtimeId, recipients, message);
            return runtimeModule.getDoService().doMulticastMessage(doMulticastMessageRequest)
                    .replaceWith(LuaValue.NIL);
        });
    }

    List<Recipient> parseRecipients(LuaTable luaRecipients) {
        final var recipients = new ArrayList<Recipient>();

        var k = LuaValue.NIL;
        while (true) {
            final var n = luaRecipients.next(k);
            k = n.arg1();
            if (k.isnil()) {
                break;
            }
            final var v = n.arg(2);

            final var userId = v.get("user_id").checklong();
            final var clientId = v.get("client_id").checklong();
            recipients.add(new Recipient(userId, clientId));
        }

        return recipients;
    }
}
