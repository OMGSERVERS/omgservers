package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.hasProjectPermissionMethod;

import com.omgservers.dto.tenantModule.HasProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface HasProjectPermissionMethod {
    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request);
}
