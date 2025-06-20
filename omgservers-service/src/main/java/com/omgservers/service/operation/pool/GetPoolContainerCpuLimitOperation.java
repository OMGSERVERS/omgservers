package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;

public interface GetPoolContainerCpuLimitOperation {
    Long execute(RuntimeQualifierEnum runtimeQualifier,
                 DeploymentConfigDto deploymentConfig);
}
