package com.omgservers.module.tenant.impl.service.stageService.impl.method.deleteStage;

import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStageMethod {
    Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request);
}
