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
class GetPoolContainerMemoryLimitOperationImpl implements GetPoolContainerMemoryLimitOperation {

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Long execute(final RuntimeQualifierEnum runtimeQualifier,
                        final DeploymentConfigDto deploymentConfig) {
        final var defaultMemoryLimit = getServiceConfigOperation.getServiceConfig()
                .runtime().defaultMemoryLimit();
        return switch (runtimeQualifier) {
            case LOBBY -> {
                if (deploymentConfig.getLobby().getMemoryLimit() > 0) {
                    yield deploymentConfig.getLobby().getMemoryLimit();
                } else {
                    yield defaultMemoryLimit;
                }
            }
            case MATCH -> {
                if (deploymentConfig.getMatch().getMemoryLimit() > 0) {
                    yield deploymentConfig.getMatch().getMemoryLimit();
                } else {
                    yield defaultMemoryLimit;
                }
            }
        };
    }
}
