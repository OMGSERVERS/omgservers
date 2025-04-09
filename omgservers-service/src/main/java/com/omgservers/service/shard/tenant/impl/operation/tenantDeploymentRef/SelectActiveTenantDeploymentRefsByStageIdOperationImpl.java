package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.tenant.impl.mapper.TenantDeploymentRefModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantDeploymentRefsByStageIdOperationImpl
        implements SelectActiveTenantDeploymentRefsByStageIdOperation {

    final SelectListOperation selectListOperation;

    final TenantDeploymentRefModelMapper tenantDeploymentRefModelMapper;

    @Override
    public Uni<List<TenantDeploymentRefModel>> execute(final SqlConnection sqlConnection,
                                                       final int shard,
                                                       final Long tenantId,
                                                       final Long stageId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, stage_id, version_id, created, modified, deployment_id,
                            deleted
                        from $shard.tab_tenant_deployment_ref
                        where tenant_id = $1 and stage_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, stageId),
                "Tenant deployment ref",
                tenantDeploymentRefModelMapper::execute);
    }
}
