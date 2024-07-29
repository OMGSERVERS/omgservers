package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.validateStageSecret;

import com.omgservers.schema.module.tenant.ValidateStageSecretRequest;
import com.omgservers.schema.module.tenant.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;

public interface ValidateStageSecretMethod {
    Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request);
}
