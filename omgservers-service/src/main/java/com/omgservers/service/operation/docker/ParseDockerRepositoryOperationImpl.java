package com.omgservers.service.operation.docker;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.service.registry.dto.DockerRegistryContainerQualifierEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryRepositoryDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ParseDockerRepositoryOperationImpl implements ParseDockerRepositoryOperation {

    @Override
    public DockerRegistryRepositoryDto parseDockerRegistryRepository(final String repository) {
        // omgservers/<tenant>/<project>/universal"
        final var parts = repository.split("/");

        if (parts.length != 4) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_ARGUMENT,
                    "repository has wrong structure");
        }

        try {
            final var namespace = parts[0];
            final var tenant = parts[1];
            final var project = parts[2];
            final var qualifier = DockerRegistryContainerQualifierEnum.fromString(parts[3]);

            return new DockerRegistryRepositoryDto(namespace, tenant, project, qualifier);
        } catch (IllegalArgumentException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_ARGUMENT, e.getMessage(), e);
        }
    }
}
