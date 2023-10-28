package com.omgservers.model.client;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {

    @NotNull
    Long id;

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    URI server;

    @NotNull
    Long connectionId;

    @NotNull
    Long versionId;

    @NotNull
    Long defaultMatchmakerId;

    @NotNull
    Long defaultRuntimeId;
}
