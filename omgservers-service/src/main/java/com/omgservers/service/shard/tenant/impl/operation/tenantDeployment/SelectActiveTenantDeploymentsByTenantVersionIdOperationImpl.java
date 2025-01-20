package com.omgservers.service.shard.tenant.impl.operation.tenantDeployment;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantDeploymentModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantDeploymentsByTenantVersionIdOperationImpl
        implements SelectActiveTenantDeploymentsByTenantVersionIdOperation {

    final SelectListOperation selectListOperation;

    final TenantDeploymentModelMapper tenantDeploymentModelMapper;

    @Override
    public Uni<List<TenantDeploymentModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long tenantVersionId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, stage_id, version_id, created, modified, queue_id, deleted
                        from $schema.tab_tenant_deployment
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, tenantVersionId),
                "Tenant deployment",
                tenantDeploymentModelMapper::fromRow);
    }
}
