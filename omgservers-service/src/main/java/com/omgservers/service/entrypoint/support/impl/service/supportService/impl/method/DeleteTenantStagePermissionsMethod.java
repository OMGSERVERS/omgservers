package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantStagePermissionsMethod {
    Uni<DeleteTenantStagePermissionsSupportResponse> execute(DeleteTenantStagePermissionsSupportRequest request);
}
