package com.omgservers.application.module.tenantModule.impl.service.stageHelpService.impl.method.validateStageSecretHelpMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request.ValidateStageSecretHelpRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageHelpService.response.ValidateStageSecretHelpResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateStageSecretHelpMethodImpl implements ValidateStageSecretHelpMethod {

    final StageInternalService stageInternalService;

    @Override
    public Uni<ValidateStageSecretHelpResponse> validateStageSecret(ValidateStageSecretHelpRequest request) {
        ValidateStageSecretHelpRequest.validateGetStageModuleRequest(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var secret = request.getSecret();
        final var getStageServiceRequest = new GetStageInternalRequest(tenantId, stageId);
        return stageInternalService.getStage(getStageServiceRequest)
                .map(response -> {
                    final var stage = response.getStage();
                    if (!stage.getSecret().equals(secret)) {
                        throw new ServerSideBadRequestException(String.format("stage secret is wrong, " +
                                "tenantId=%s, stageId=%s", tenantId, stageId));
                    }
                    return stage;
                })
                .map(ValidateStageSecretHelpResponse::new);
    }
}
