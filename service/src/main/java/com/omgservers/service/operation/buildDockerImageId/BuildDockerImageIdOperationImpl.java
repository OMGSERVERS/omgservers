package com.omgservers.service.operation.buildDockerImageId;

import com.omgservers.model.dockerRepository.DockerRepositoryModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BuildDockerImageIdOperationImpl implements BuildDockerImageIdOperation {

    @Override
    public String buildDockerImageId(final DockerRepositoryModel dockerRepository,
                                     final Long versionId) {
        final var repositoryString = dockerRepository.toString();
        final var imageId = String.format("%s:%d", repositoryString, versionId);
        return imageId;
    }
}
