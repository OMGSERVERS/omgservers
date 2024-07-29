package com.omgservers.service.operation.parseDockerRepository;

import com.omgservers.schema.service.registry.DockerRegistryRepositoryDto;

public interface ParseDockerRepositoryOperation {

    DockerRegistryRepositoryDto parseDockerRegistryRepository(String repository);
}
