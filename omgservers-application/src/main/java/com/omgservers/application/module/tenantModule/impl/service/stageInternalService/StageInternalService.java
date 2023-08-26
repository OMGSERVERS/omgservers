package com.omgservers.application.module.tenantModule.impl.service.stageInternalService;

import com.omgservers.dto.tenantModule.DeleteStageRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface StageInternalService {

    Uni<GetStageInternalResponse> getStage(GetStageRoutedRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageRoutedRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageRoutedRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionRoutedRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionRoutedRequest request);
}
