package com.omgservers.module.tenant.impl.service.stageService;

import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.tenant.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;

public interface StageService {
    Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request);
}