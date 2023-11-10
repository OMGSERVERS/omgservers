package com.omgservers.worker.module.handler.lua.operation.mapLuaCommand.mapper;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.doCommand.body.DoSetAttributesCommandBodyModel;
import com.omgservers.model.luaCommand.LuaCommandQualifierEnum;
import com.omgservers.model.player.PlayerAttributeModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
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
    public DoCommandModel map(final LuaContext luaContext, LuaTable luaCommand) {
        final var userId = Long.valueOf(luaCommand.get("user_id").checkjstring());
        final var clientId = Long.valueOf(luaCommand.get("client_id").checkjstring());

        final var luaAttributes = luaCommand.get("attributes").checktable();
        luaContext.updateAttributes(userId, luaAttributes);

        final var attributes = parseAttributes(luaAttributes);

        final var doCommandBody = new DoSetAttributesCommandBodyModel(userId, clientId, attributes);
        final var doCommandModel = new DoCommandModel(DoCommandQualifierEnum.DO_SET_ATTRIBUTES, doCommandBody);
        return doCommandModel;
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
