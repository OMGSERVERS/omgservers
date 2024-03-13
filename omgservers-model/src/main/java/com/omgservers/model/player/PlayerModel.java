package com.omgservers.model.player;

import jakarta.validation.constraints.NotBlank;
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

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

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
    Object profile;

    @NotNull
    Boolean deleted;
}
