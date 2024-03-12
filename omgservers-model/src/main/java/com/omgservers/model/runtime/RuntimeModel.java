package com.omgservers.model.runtime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeModel {

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
    RuntimeQualifierEnum qualifier;

    @NotNull
    Long userId;

    @NotNull
    Instant lastActivity;

    @NotNull
    RuntimeConfigModel config;

    @NotNull
    Boolean deleted;
}
