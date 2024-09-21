package com.omgservers.schema.model.runtime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long tenantId;

    @NotNull
    Long deploymentId;

    @NotNull
    RuntimeQualifierEnum qualifier;

    @NotNull
    Long userId;

    @NotNull
    Instant lastActivity;

    @NotNull
    RuntimeConfigDto config;

    @NotNull
    Boolean deleted;
}
