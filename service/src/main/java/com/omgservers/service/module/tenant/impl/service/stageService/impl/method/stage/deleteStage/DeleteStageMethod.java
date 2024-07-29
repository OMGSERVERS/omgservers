package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.deleteStage;

import com.omgservers.schema.module.tenant.DeleteStageRequest;
import com.omgservers.schema.module.tenant.DeleteStageResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStageMethod {
    Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request);
}
