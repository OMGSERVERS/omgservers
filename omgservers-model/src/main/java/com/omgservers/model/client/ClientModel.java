package com.omgservers.model.client;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    Long matchmakerId;

    @NotNull
    Boolean deleted;
}
