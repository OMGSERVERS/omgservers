package com.omgservers.service.service.registry.operation.intersectDockerRegistryScope;

import com.omgservers.schema.service.registry.DockerRegistryAccessDto;
import com.omgservers.schema.service.registry.DockerRegistryScopeDto;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface IntersectDockerRegistryScopeOperation {

    Uni<List<DockerRegistryAccessDto>> intersectDockerRegistryScope(Long userId, DockerRegistryScopeDto requestedScope);
}
