package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod;

import com.omgservers.dto.tenantModule.DeleteStageInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStageMethod {
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request);
}
