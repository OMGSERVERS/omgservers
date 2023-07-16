package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
public class LuaPlayerSignedInEvent extends LuaEvent {

    final UUID user;
    final UUID player;
    final UUID client;

    public LuaPlayerSignedInEvent(UUID user, UUID player, UUID client) {
        super("player_signed_in");
        this.user = user;
        this.player = player;
        this.client = client;
        set("user_uuid", user.toString());
        set("player_uuid", player.toString());
        set("client_uuid", client.toString());
    }
}
