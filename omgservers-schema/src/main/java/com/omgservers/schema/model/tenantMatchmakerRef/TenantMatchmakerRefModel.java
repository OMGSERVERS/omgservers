package com.omgservers.schema.model.tenantMatchmakerRef;

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
public class TenantMatchmakerRefModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long deploymentId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Long matchmakerId;

    @NotNull
    Boolean deleted;
}
