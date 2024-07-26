package com.omgservers.service.operation.parseDockerRepository;

import com.omgservers.model.dockerRepository.DockerRepositoryModel;

public interface ParseDockerRepositoryOperation {

    DockerRepositoryModel parseDockerRegistryRepository(String repository);
}
