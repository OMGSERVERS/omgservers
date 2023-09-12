package com.omgservers.model.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerModel {

    public static void validate(PlayerModel player) {
        if (player == null) {
            throw new IllegalArgumentException("player is null");
        }
    }

    Long id;
    Long userId;
    Instant created;
    Instant modified;
    Long tenantId;
    Long stageId;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    PlayerConfigModel config;
}
