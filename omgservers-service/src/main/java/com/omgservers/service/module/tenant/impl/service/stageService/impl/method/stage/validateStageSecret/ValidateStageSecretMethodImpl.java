package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.validateStageSecret;

import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.ValidateStageSecretRequest;
import com.omgservers.schema.module.tenant.ValidateStageSecretResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.module.tenant.impl.service.stageService.StageService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateStageSecretMethodImpl implements ValidateStageSecretMethod {

    final StageService stageService;

    @Override
    public Uni<ValidateStageSecretResponse> validateStageSecret(final ValidateStageSecretRequest request) {
        log.debug("Validate stage secret, request={}", request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var secret = request.getSecret();
        final var getStageServiceRequest = new GetStageRequest(tenantId, stageId);
        return stageService.getStage(getStageServiceRequest)
                .map(response -> {
                    final var stage = response.getStage();
                    if (!stage.getSecret().equals(secret)) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_STAGE_SECRET,
                                String.format("stage secret is wrong, tenantId=%s, stageId=%s", tenantId, stageId));
                    }
                    return stage;
                })
                .map(ValidateStageSecretResponse::new);
    }
}
