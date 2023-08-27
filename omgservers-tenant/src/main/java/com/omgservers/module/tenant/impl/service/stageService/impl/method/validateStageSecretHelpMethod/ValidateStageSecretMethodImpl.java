package com.omgservers.module.tenant.impl.service.stageService.impl.method.validateStageSecretHelpMethod;

import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.tenant.ValidateStageSecretResponse;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateStageSecretMethodImpl implements ValidateStageSecretMethod {

    final StageShardedService stageShardedService;

    @Override
    public Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request) {
        ValidateStageSecretRequest.validateGetStageModuleRequest(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var secret = request.getSecret();
        final var getStageServiceRequest = new GetStageShardedRequest(tenantId, stageId);
        return stageShardedService.getStage(getStageServiceRequest)
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
