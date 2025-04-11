package com.omgservers.schema.model.deployment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentConfigDto {

    static public DeploymentConfigDto create() {
        final var deploymentConfig = new DeploymentConfigDto();
        deploymentConfig.setVersion(DeploymentConfigVersionEnum.V1);
        return deploymentConfig;
    }

    @NotNull
    DeploymentConfigVersionEnum version;
}
