package com.omgservers.application.module.tenantModule.impl.service.stageInternalService;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.*;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface StageInternalService {

    Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request);

    Uni<Void> syncStage(SyncStageInternalRequest request);

    Uni<Void> deleteStage(DeleteStageInternalRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);
}
