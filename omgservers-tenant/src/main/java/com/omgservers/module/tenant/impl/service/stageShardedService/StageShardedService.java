package com.omgservers.module.tenant.impl.service.stageShardedService;

import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface StageShardedService {

    Uni<GetStageShardedResponse> getStage(GetStageShardedRequest request);

    Uni<SyncStageShardedResponse> syncStage(SyncStageShardedRequest request);

    Uni<DeleteStageShardedResponse> deleteStage(DeleteStageShardedRequest request);

    Uni<HasStagePermissionShardedResponse> hasStagePermission(HasStagePermissionShardedRequest request);

    Uni<SyncStagePermissionShardedResponse> syncStagePermission(SyncStagePermissionShardedRequest request);
}
