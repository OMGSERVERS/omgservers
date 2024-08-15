package com.omgservers.service.service.registry.operation.parseDockerRegistryScope;

import com.omgservers.service.service.registry.dto.DockerRegistryScopeDto;

public interface ParseDockerRegistryScopeOperation {

    DockerRegistryScopeDto parseDockerRegistryScope(String scope);
}
