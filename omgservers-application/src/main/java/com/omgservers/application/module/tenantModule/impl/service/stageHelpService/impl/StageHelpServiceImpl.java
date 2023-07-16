package com.omgservers.application.module.tenantModule.impl.service.stageHelpService.impl;

import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.StageHelpService;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.impl.method.validateStageSecretHelpMethod.ValidateStageSecretHelpMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request.ValidateStageSecretHelpRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.response.ValidateStageSecretHelpResponse;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class StageHelpServiceImpl implements StageHelpService {

    final ValidateStageSecretHelpMethod validateStageSecretMethod;

    @Override
    public Uni<ValidateStageSecretHelpResponse> validateStageSecret(ValidateStageSecretHelpRequest request) {
        return validateStageSecretMethod.validateStageSecret(request);
    }
}
