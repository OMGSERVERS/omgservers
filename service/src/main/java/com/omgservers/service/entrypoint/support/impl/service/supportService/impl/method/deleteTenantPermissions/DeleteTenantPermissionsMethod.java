package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteTenantPermissions;

import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantPermissionsMethod {
    Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(DeleteTenantPermissionsSupportRequest request);
}
