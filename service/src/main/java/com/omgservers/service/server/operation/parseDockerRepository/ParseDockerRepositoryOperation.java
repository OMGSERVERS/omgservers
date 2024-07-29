package com.omgservers.service.server.operation.parseDockerRepository;

import com.omgservers.schema.service.registry.DockerRegistryRepositoryDto;

public interface ParseDockerRepositoryOperation {

    DockerRegistryRepositoryDto parseDockerRegistryRepository(String repository);
}
