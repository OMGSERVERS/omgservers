package com.omgservers.module.tenant.impl.service.stageService.impl.method.getStageVersion;

import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.GetStageVersionRequest;
import com.omgservers.dto.tenant.GetStageVersionResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.stage.StageModel;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetStageVersionMethodImpl implements GetStageVersionMethod {

    final TenantModule tenantModule;

    @Override
    public Uni<GetStageVersionResponse> getStageVersion(final GetStageVersionRequest request) {
        GetStageVersionRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();

        final var getStageShardedRequest = new GetStageShardedRequest(tenantId, stageId);
        return tenantModule.getStageShardedService().getStage(getStageShardedRequest)
                .map(GetStageShardedResponse::getStage)
                .map(StageModel::getVersionId)
                .onItem().ifNull().failWith(new ServerSideConflictException(String
                        .format("no any stage's version wasn't deployed yet, " +
                                "tenantId=%d, stageId=%d", tenantId, stageId)))
                .map(GetStageVersionResponse::new);
    }
}
