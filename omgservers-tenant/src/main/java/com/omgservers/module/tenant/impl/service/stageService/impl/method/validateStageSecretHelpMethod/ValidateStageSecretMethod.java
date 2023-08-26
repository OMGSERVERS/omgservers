package com.omgservers.module.tenant.impl.service.stageService.impl.method.validateStageSecretHelpMethod;

import com.omgservers.dto.tenantModule.ValidateStageSecretRequest;
import com.omgservers.dto.tenantModule.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateStageSecretMethod {
    Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request);
}
