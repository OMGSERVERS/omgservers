package com.omgservers.schema.model.tenantStageChangeOfState;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import jakarta.validation.constraints.NotNull;

public record TenantStageDeploymentResourceToUpdateStatusDto(@NotNull Long id,
                                                             @NotNull TenantDeploymentResourceStatusEnum status) {
}
