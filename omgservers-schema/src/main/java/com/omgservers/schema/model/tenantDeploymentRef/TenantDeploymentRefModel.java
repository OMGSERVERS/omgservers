package com.omgservers.schema.model.tenantDeploymentRef;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDeploymentRefModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Long versionId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long deploymentId;

    @NotNull
    Boolean deleted;
}
