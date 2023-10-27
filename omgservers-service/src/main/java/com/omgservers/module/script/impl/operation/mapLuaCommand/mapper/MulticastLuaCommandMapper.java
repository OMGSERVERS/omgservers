package com.omgservers.module.script.impl.operation.mapLuaCommand.mapper;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.body.MulticastRequestedEventBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.script.impl.operation.mapLuaCommand.LuaCommandMapper;
import com.omgservers.module.system.factory.EventModelFactory;
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

    final EventModelFactory eventModelFactory;

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.MULTICAST;
    }

    @Override
    public EventModel map(final ScriptModel script, LuaTable luaCommand) {
        final var runtimeId = script.getRuntimeId();

        final var luaRecipients = luaCommand.get("recipients").checktable();
        final var recipients = parseRecipients(luaRecipients);
        final var luaMessage = luaCommand.get("message").checktable();

        final var eventBody = new MulticastRequestedEventBodyModel(runtimeId, recipients, luaMessage);
        final var eventModel = eventModelFactory.create(eventBody);
        return eventModel;
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
