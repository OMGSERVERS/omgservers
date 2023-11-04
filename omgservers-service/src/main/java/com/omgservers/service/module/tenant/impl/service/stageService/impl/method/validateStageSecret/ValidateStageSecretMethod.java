package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.validateStageSecret;

import com.omgservers.model.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.model.dto.tenant.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateStageSecretMethod {
    Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request);
}
