package com.omgservers.model.versionMatchmaker;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionMatchmakerModel {

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
    Long matchmakerId;

    @NotNull
    Boolean deleted;
}
