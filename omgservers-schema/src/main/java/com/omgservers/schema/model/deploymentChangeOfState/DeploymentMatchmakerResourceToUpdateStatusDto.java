package com.omgservers.schema.model.deploymentChangeOfState;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import jakarta.validation.constraints.NotNull;

public record DeploymentMatchmakerResourceToUpdateStatusDto(@NotNull Long id,
                                                            @NotNull DeploymentMatchmakerResourceStatusEnum status) {
}
