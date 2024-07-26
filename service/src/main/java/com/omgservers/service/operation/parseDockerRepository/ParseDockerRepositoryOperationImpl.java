package com.omgservers.service.operation.parseDockerRepository;

import com.omgservers.model.dockerRepository.DockerContainerQualifierEnum;
import com.omgservers.model.dockerRepository.DockerRepositoryModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ParseDockerRepositoryOperationImpl implements ParseDockerRepositoryOperation {

    @Override
    public DockerRepositoryModel parseDockerRegistryRepository(final String repository) {
        // omgservers/245515657456648192/231077687903387648/231939082811342849/universal"
        final var parts = repository.split("/");

        if (parts.length != 5) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.ARGUMENT_WRONG);
        }

        try {
            final var namespace = parts[0];
            final var tenantId = Long.parseLong(parts[1]);
            final var projectId = Long.parseLong(parts[2]);
            final var stageId = Long.parseLong(parts[3]);
            final var qualifier = DockerContainerQualifierEnum.fromString(parts[4]);

            return new DockerRepositoryModel(namespace, tenantId, projectId, stageId, qualifier);
        } catch (IllegalArgumentException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.ARGUMENT_WRONG, e.getMessage(), e);
        }
    }
}
