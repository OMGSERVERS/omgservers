package com.omgservers.schema.model.deploymentChangeOfState;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import jakarta.validation.constraints.NotNull;

public record DeploymentLobbyResourceToUpdateStatusDto(@NotNull Long id,
                                                       @NotNull DeploymentLobbyResourceStatusEnum status) {
}
