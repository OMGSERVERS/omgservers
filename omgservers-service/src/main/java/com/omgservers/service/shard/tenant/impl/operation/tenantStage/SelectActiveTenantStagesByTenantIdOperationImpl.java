package com.omgservers.service.shard.tenant.impl.operation.tenantStage;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantStageModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantStagesByTenantIdOperationImpl implements SelectActiveTenantStagesByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantStageModelMapper tenantStageModelMapper;

    @Override
    public Uni<List<TenantStageModel>> execute(final SqlConnection sqlConnection,
                                               final int slot,
                                               final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, config, deleted
                        from $slot.tab_tenant_stage
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Tenant stage",
                tenantStageModelMapper::execute);
    }
}
