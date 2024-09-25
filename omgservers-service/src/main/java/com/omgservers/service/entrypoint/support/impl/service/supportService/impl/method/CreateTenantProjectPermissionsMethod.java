package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectPermissionsMethod {
    Uni<CreateTenantProjectPermissionsSupportResponse> execute(CreateTenantProjectPermissionsSupportRequest request);
}
