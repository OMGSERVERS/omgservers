package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.DeleteStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.DeleteStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStageMethod {
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request);
}
