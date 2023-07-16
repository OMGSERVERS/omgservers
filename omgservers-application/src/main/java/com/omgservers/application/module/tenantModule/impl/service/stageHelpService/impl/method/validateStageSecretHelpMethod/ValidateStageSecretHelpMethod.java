package com.omgservers.application.module.tenantModule.impl.service.stageHelpService.impl.method.validateStageSecretHelpMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request.ValidateStageSecretHelpRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.response.ValidateStageSecretHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateStageSecretHelpMethod {
    Uni<ValidateStageSecretHelpResponse> validateStageSecret(ValidateStageSecretHelpRequest request);
}
