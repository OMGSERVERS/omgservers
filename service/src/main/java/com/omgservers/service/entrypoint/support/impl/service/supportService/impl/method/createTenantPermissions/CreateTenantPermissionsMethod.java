package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createTenantPermissions;

import com.omgservers.model.dto.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantPermissionsMethod {
    Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(CreateTenantPermissionsSupportRequest request);
}
