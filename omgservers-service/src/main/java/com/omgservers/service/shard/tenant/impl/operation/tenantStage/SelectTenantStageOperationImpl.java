package com.omgservers.service.shard.tenant.impl.operation.tenantStage;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantStageModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantStageOperationImpl implements SelectTenantStageOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantStageModelMapper tenantStageModelMapper;

    @Override
    public Uni<TenantStageModel> execute(final SqlConnection sqlConnection,
                                         final int slot,
                                         final Long tenantId,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, config, deleted
                        from $slot.tab_tenant_stage
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Stage",
                tenantStageModelMapper::execute);
    }
}
