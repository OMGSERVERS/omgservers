package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
@ToString
public class SignInLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;
    final LuaTable attributes;
    final LuaValue object;

    public SignInLuaRequest(final Long userId,
                            final Long clientId,
                            final LuaTable attributes,
                            final LuaValue object) {
        super("sign_in", true);
        this.userId = userId;
        this.clientId = clientId;
        this.attributes = attributes;
        this.object = object;
        set("user_id", userId.toString());
        set("client_id", clientId.toString());
        set("attributes", attributes);
        set("object", object);
    }
}
