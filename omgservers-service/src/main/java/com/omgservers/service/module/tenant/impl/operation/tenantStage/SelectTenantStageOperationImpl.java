package com.omgservers.service.module.tenant.impl.operation.tenantStage;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantStageModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
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
                                         final int shard,
                                         final Long tenantId,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, secret, deleted
                        from $schema.tab_tenant_stage
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        tenantId,
                        id
                ),
                "Stage",
                tenantStageModelMapper::fromRow);
    }
}
