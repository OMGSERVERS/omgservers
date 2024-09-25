package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantPermissionsMethod {
    Uni<CreateTenantPermissionsSupportResponse> execute(CreateTenantPermissionsSupportRequest request);
}
