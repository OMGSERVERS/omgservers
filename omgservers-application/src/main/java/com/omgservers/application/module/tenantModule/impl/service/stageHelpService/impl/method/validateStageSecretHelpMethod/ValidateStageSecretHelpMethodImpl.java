package com.omgservers.application.module.tenantModule.impl.service.stageHelpService.impl.method.validateStageSecretHelpMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request.ValidateStageSecretHelpRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.response.ValidateStageSecretHelpResponse;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateStageSecretHelpMethodImpl implements ValidateStageSecretHelpMethod {

    final StageInternalService stageInternalService;

    @Override
    public Uni<ValidateStageSecretHelpResponse> validateStageSecret(ValidateStageSecretHelpRequest request) {
        ValidateStageSecretHelpRequest.validateGetStageModuleRequest(request);

        final var tenant = request.getTenant();
        final var stageUuid = request.getStage();
        final var secret = request.getSecret();
        final var getStageServiceRequest = new GetStageInternalRequest(tenant, stageUuid);
        return stageInternalService.getStage(getStageServiceRequest)
                .map(response -> {
                    final var stage = response.getStage();
                    if (!stage.getSecret().equals(secret)) {
                        throw new ServerSideBadRequestException(String.format("stage secret is wrong, " +
                                "tenant=%s, stageUuid=%s", tenant, stageUuid));
                    }
                    return stage;
                })
                .map(ValidateStageSecretHelpResponse::new);
    }
}
