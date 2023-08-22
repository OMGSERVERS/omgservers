package com.omgservers.application.module.tenantModule.impl.service.stageInternalService;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.DeleteStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.HasStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.DeleteStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface StageInternalService {

    Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);
}
