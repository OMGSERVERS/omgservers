package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.deleteStage;

import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStageMethod {
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardRequest request);
}
