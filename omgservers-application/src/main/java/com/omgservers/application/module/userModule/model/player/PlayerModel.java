package com.omgservers.application.module.userModule.model.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerModel {

    static public void validate(PlayerModel player) {
        if (player == null) {
            throw new IllegalArgumentException("player is null");
        }
    }

    Long id;
    Long userId;
    Instant created;
    Instant modified;
    Long stageId;
    @ToString.Exclude
    PlayerConfigModel config;
}
