package com.omgservers.service.operation.buildDockerImageId;

import com.omgservers.model.dockerRepository.DockerRepositoryModel;

public interface BuildDockerImageIdOperation {

    String buildDockerImageId(DockerRepositoryModel dockerRepository,
                                             Long versionId);
}
