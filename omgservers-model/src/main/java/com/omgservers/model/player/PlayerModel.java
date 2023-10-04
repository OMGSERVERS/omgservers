package com.omgservers.model.player;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    Long id;

    @NotNull
    Long userId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    PlayerAttributesModel attributes;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    PlayerObjectModel object;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    PlayerConfigModel config;
}
