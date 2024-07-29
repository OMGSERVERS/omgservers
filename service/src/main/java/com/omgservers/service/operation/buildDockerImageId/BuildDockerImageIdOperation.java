package com.omgservers.service.operation.buildDockerImageId;

import com.omgservers.schema.service.registry.DockerRegistryRepositoryDto;

public interface BuildDockerImageIdOperation {

    String buildDockerImageId(DockerRegistryRepositoryDto dockerRepository,
                              Long versionId);
}
