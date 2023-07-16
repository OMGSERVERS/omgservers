package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.DeleteStageInternalRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteStageMethod {
    Uni<Void> deleteStage(DeleteStageInternalRequest request);
}
