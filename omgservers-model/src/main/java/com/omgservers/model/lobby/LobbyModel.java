package com.omgservers.model.lobby;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LobbyModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    Long runtimeId;

    @NotNull
    Boolean deleted;
}
