package com.omgservers.model.versionLobbyRef;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionLobbyRefModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long lobbyId;

    @NotNull
    Boolean deleted;
}
