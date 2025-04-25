package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteProjectPermissionsMethod {
    Uni<DeleteTenantProjectPermissionsSupportResponse> execute(DeleteTenantProjectPermissionsSupportRequest request);
}
