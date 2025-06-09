package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantStageState.TenantStageStateDto;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateResponse;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class FetchStageOperationImpl implements FetchStageOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<FetchTenantStageResult> execute(final Long tenantId, final Long tenantStageId) {
        return getTenantStageState(tenantId, tenantStageId)
                .map(tenantStageState -> new FetchTenantStageResult(tenantId,
                        tenantStageId,
                        tenantStageState));
    }

    Uni<TenantStageStateDto> getTenantStageState(final Long tenantId, final Long id) {
        final var request = new GetTenantStageStateRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageStateResponse::getTenantStageState);
    }
}
