package com.omgservers.service.operation.docker;

import com.omgservers.service.service.registry.dto.DockerRegistryRepositoryDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BuildDockerImageIdOperationImpl implements BuildDockerImageIdOperation {

    @Override
    public String buildDockerImageId(final DockerRegistryRepositoryDto dockerRepository,
                                     final Long tenantVersionId) {
        final var repositoryString = dockerRepository.toString();
        final var imageId = String.format("%s:%d", repositoryString, tenantVersionId);
        return imageId;
    }
}
