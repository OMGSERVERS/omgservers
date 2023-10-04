package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.player.PlayerAttributeModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
@AllArgsConstructor
public class LuaPlayerSetAttributesFunction extends VarArgFunction {
    private static final long TIMEOUT = 10L;

    @ToString.Exclude
    final UserModule userModule;

    @ToString.Exclude
    final HandleLuaCallOperation handleLuaCallOperation;

    final Long userId;
    final Long playerId;

    @Override
    public Varargs invoke(Varargs args) {
        return handleLuaCallOperation.handleLuaCall(() -> {
            final var luaAttributes = args.arg(1).checktable();
            final var attributes = parseAttributes(luaAttributes);

            final var request = new UpdatePlayerAttributesRequest(userId, playerId,
                    new PlayerAttributesModel(attributes));
            return userModule.getPlayerService().updatePlayerAttributes(request)
                    .replaceWith(LuaValue.NIL);
        });
    }

    List<PlayerAttributeModel> parseAttributes(LuaTable luaAttributes) {
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

        return attributes;
    }
}
