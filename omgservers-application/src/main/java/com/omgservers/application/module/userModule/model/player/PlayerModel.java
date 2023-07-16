package com.omgservers.application.module.userModule.model.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerModel {

    static public PlayerModel create(final UUID user,
                                     final UUID stage,
                                     final PlayerConfigModel config) {
        return create(user, UUID.randomUUID(), stage, config);
    }

    static public PlayerModel create(final UUID user,
                                     final UUID uuid,
                                     final UUID stage,
                                     final PlayerConfigModel config) {
        Instant now = Instant.now();

        PlayerModel player = new PlayerModel();
        player.setUser(user);
        player.setCreated(now);
        player.setModified(now);
        player.setUuid(uuid);
        player.setStage(stage);
        player.setConfig(config);

        return player;
    }

    static public void validatePlayer(PlayerModel player) {
        if (player == null) {
            throw new IllegalArgumentException("player is null");
        }
    }

    UUID user;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    UUID stage;
    @ToString.Exclude
    PlayerConfigModel config;
}
