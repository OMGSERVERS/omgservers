package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod;

import com.omgservers.dto.tenantModule.DeleteStageRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteStageMethod {
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageRoutedRequest request);
}
