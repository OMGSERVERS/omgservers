package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteTenantPermissions;

import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionsMethod {
    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(DeleteTenantPermissionsSupportRequest request);
}
