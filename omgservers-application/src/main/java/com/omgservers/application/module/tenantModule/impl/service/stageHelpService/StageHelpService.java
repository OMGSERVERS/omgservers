package com.omgservers.application.module.tenantModule.impl.service.stageHelpService;

import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request.ValidateStageSecretHelpRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.response.ValidateStageSecretHelpResponse;
import io.smallrye.mutiny.Uni;

public interface StageHelpService {
    Uni<ValidateStageSecretHelpResponse> validateStageSecret(ValidateStageSecretHelpRequest request);
}
