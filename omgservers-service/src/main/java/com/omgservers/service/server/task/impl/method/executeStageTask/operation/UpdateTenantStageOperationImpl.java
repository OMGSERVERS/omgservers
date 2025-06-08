package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantStageChangeOfState.TenantStageChangeOfStateDto;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateResponse;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class UpdateTenantStageOperationImpl implements UpdateTenantStageOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final HandleTenantStageResult handleTenantStageResult) {
        final var tenantId = handleTenantStageResult.tenantId();
        final var tenantStageId = handleTenantStageResult.tenantStageId();
        final var tenantStageChangeOfState = handleTenantStageResult.tenantStageChangeOfState();

        return updateMatchmakerState(tenantId, tenantStageId, tenantStageChangeOfState)
                .replaceWithVoid();
    }

    Uni<Boolean> updateMatchmakerState(final Long tenantId,
                                       final Long tenantStageId,
                                       final TenantStageChangeOfStateDto tenantStageChangeOfStateDto) {
        final var request = new UpdateTenantStageStateRequest(tenantId, tenantStageId, tenantStageChangeOfStateDto);
        return tenantShard.getService().execute(request)
                .map(UpdateTenantStageStateResponse::getUpdated);
    }
}
