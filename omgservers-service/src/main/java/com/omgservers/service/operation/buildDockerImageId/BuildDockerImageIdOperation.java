package com.omgservers.service.operation.buildDockerImageId;

import com.omgservers.service.service.registry.dto.DockerRegistryRepositoryDto;

public interface BuildDockerImageIdOperation {

    String buildDockerImageId(DockerRegistryRepositoryDto dockerRepository,
                              Long versionId);
}
