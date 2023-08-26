package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.hasProjectPermission;

import com.omgservers.dto.tenantModule.HasProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface HasProjectPermissionMethod {
    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardRequest request);
}
