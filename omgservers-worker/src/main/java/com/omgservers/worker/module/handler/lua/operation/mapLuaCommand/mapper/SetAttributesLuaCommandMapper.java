package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.model.outgoingCommand.body.SetAttributesOutgoingCommandBodyModel;
import com.omgservers.model.player.PlayerAttributeModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.LuaCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SetAttributesLuaCommandMapper implements LuaCommandMapper {

    @Override
    public LuaCommandQualifierEnum getQualifier() {
        return LuaCommandQualifierEnum.SET_ATTRIBUTES;
    }

    @Override
    public OutgoingCommandModel map(LuaTable luaCommand) {
        final var clientId = Long.valueOf(luaCommand.get("client_id").checkjstring());
        final var luaAttributes = luaCommand.get("attributes").checktable();
        final var attributes = parseAttributes(luaAttributes);

        final var outgoingCommandBody = new SetAttributesOutgoingCommandBodyModel(clientId, attributes);
        final var outgoingCommand = new OutgoingCommandModel(OutgoingCommandQualifierEnum.SET_ATTRIBUTES,
                outgoingCommandBody);
        return outgoingCommand;
    }

    PlayerAttributesModel parseAttributes(LuaTable luaAttributes) {
        final var attributes = new ArrayList<PlayerAttributeModel>();

        var k = LuaValue.NIL;
        while (true) {
            final var n = luaAttributes.next(k);
            k = n.arg1();
            if (k.isnil()) {
                break;
            }
            final var v = n.arg(2);

            final var name = k.checkjstring();
            switch (v.type()) {
                case LuaValue.TBOOLEAN:
                    attributes.add(PlayerAttributeModel.create(name, v.checkboolean()));
                    break;
                case LuaValue.TNUMBER:
                    if (v.isint()) {
                        attributes.add(PlayerAttributeModel.create(name, v.checklong()));
                    } else {
                        attributes.add(PlayerAttributeModel.create(name, v.checkdouble()));
                    }
                    break;
                case LuaValue.TSTRING:
                    attributes.add(PlayerAttributeModel.create(name, v.checkjstring()));
                    break;
            }
        }

        return new PlayerAttributesModel(attributes);
    }
}
