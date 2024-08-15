package com.omgservers.service.service.registry.operation.intersectDockerRegistryScope;

import com.omgservers.service.service.registry.dto.DockerRegistryAccessDto;
import com.omgservers.service.service.registry.dto.DockerRegistryScopeDto;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface IntersectDockerRegistryScopeOperation {

    Uni<List<DockerRegistryAccessDto>> intersectDockerRegistryScope(Long userId, DockerRegistryScopeDto requestedScope);
}
