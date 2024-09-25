package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionsMethod {
    Uni<DeleteTenantPermissionsSupportResponse> execute(DeleteTenantPermissionsSupportRequest request);
}
