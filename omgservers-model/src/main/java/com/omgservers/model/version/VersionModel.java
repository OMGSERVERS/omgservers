package com.omgservers.model.version;

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
public class VersionModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long defaultMatchmakerId;

    @NotNull
    Long defaultRuntimeId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    VersionConfigModel config;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    VersionSourceCodeModel sourceCode;

    @NotNull
    Boolean deleted;
}
