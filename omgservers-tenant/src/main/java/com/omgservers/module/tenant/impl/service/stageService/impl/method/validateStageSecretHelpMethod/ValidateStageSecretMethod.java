package com.omgservers.module.tenant.impl.service.stageService.impl.method.validateStageSecretHelpMethod;

import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.tenant.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateStageSecretMethod {
    Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request);
}
