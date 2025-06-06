package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetPoolContainerCpuLimitOperationImpl implements GetPoolContainerCpuLimitOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Long execute(final RuntimeQualifierEnum runtimeQualifier,
                        final DeploymentConfigDto deploymentConfig) {
        final var defaultCpuLimit = getServiceConfigOperation.getServiceConfig()
                .runtime().defaultCpuLimit();
        return switch (runtimeQualifier) {
            case LOBBY -> {
                if (deploymentConfig.getLobby().getCpuLimit() > 0) {
                    yield deploymentConfig.getLobby().getCpuLimit();
                } else {
                    yield defaultCpuLimit;
                }
            }
            case MATCH -> {
                if (deploymentConfig.getMatch().getCpuLimit() > 0) {
                    yield deploymentConfig.getMatch().getCpuLimit();
                } else {
                    yield defaultCpuLimit;
                }
            }
        };
    }
}
