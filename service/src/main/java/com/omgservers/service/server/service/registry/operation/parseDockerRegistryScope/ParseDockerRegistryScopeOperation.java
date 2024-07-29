package com.omgservers.service.server.service.registry.operation.parseDockerRegistryScope;

import com.omgservers.schema.service.registry.DockerRegistryScopeDto;

public interface ParseDockerRegistryScopeOperation {

    DockerRegistryScopeDto parseDockerRegistryScope(String scope);
}
