package com.omgservers.schema.model.tenantDeploymentResource;

import jakarta.validation.constraints.NotBlank;
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
public class TenantDeploymentResourceModel {

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
    TenantDeploymentResourceStatusEnum status;

    @NotNull
    TenantDeploymentResourceConfigDto config;

    @NotNull
    Boolean deleted;
}
