package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.viewStagePermissions;

import com.omgservers.schema.module.tenant.ViewStagePermissionsRequest;
import com.omgservers.schema.module.tenant.ViewStagePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewStagePermissionsMethod {
    Uni<ViewStagePermissionsResponse> viewStagePermissions(ViewStagePermissionsRequest request);
}
