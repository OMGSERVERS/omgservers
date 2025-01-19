package com.omgservers.service.operation.docker;

import com.omgservers.service.service.registry.dto.DockerRegistryRepositoryDto;

public interface BuildDockerImageIdOperation {

    String buildDockerImageId(DockerRegistryRepositoryDto dockerRepository,
                              Long tenantVersionId);
}
