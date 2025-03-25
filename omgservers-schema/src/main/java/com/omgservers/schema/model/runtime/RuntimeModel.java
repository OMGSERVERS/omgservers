package com.omgservers.schema.model.runtime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long deploymentId;

    @NotNull
    RuntimeQualifierEnum qualifier;

    @NotNull
    Long userId;

    @NotNull
    @ToString.Exclude
    RuntimeConfigDto config;

    @NotNull
    Boolean deleted;
}
