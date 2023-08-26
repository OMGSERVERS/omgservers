package com.omgservers.module.tenant.impl.service.stageService;

import com.omgservers.dto.tenantModule.ValidateStageSecretRequest;
import com.omgservers.dto.tenantModule.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;

public interface StageService {
    Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request);
}
