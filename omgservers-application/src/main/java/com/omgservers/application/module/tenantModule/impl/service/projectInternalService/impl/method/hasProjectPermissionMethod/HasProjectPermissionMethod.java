package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.hasProjectPermissionMethod;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.HasProjectPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.HasProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface HasProjectPermissionMethod {
    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request);
}
