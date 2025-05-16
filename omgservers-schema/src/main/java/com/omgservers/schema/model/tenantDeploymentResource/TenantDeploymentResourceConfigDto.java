package com.omgservers.schema.model.tenantDeploymentResource;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDeploymentResourceConfigDto {

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    TenantDeploymentResourceConfigVersionEnum version = TenantDeploymentResourceConfigVersionEnum.V1;

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    DeploymentConfigDto deploymentConfig = new DeploymentConfigDto();
}
