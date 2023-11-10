package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoMulticastCommandBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MulticastLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.MULTICAST;
    }

    @Override
    public DoCommandModel map(final LuaContext luaContext, LuaTable luaCommand) {
        final var luaRecipients = luaCommand.get("recipients").checktable();
        final var recipients = parseRecipients(luaRecipients);
        final var luaMessage = luaCommand.get("message").checktable();

        final var doCommandBody = new DoMulticastCommandBodyModel(recipients, luaMessage);
        final var doCommandModel = new DoCommandModel(DoCommandQualifierEnum.DO_MULTICAST, doCommandBody);
        return doCommandModel;
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
