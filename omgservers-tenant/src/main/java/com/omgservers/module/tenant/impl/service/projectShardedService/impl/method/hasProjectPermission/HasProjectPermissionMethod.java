package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.hasProjectPermission;

import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface HasProjectPermissionMethod {
    Uni<HasProjectPermissionShardedResponse> hasProjectPermission(HasProjectPermissionShardedRequest request);
}