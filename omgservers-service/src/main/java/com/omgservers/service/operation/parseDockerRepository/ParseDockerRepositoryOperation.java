package com.omgservers.service.operation.parseDockerRepository;

import com.omgservers.service.service.registry.dto.DockerRegistryRepositoryDto;

public interface ParseDockerRepositoryOperation {

    DockerRegistryRepositoryDto parseDockerRegistryRepository(String repository);
}
