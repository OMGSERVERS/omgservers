package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.viewStagePermissions;

import com.omgservers.model.dto.tenant.ViewStagePermissionsRequest;
import com.omgservers.model.dto.tenant.ViewStagePermissionsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewStagePermissionsMethod {
    Uni<ViewStagePermissionsResponse> viewStagePermissions(ViewStagePermissionsRequest request);
}
