package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantStagePermissionsMethod {
    Uni<CreateTenantStagePermissionsSupportResponse> execute(CreateTenantStagePermissionsSupportRequest request);
}
