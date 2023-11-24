package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.validateStageSecret;

import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.model.dto.tenant.ValidateStageSecretResponse;
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
                        throw new ServerSideBadRequestException(String.format("stage secret is wrong, " +
                                "tenantId=%s, stageId=%s", tenantId, stageId));
                    }
                    return stage;
                })
                .map(ValidateStageSecretResponse::new);
    }
}
