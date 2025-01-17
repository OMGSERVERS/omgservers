package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantDeploymentModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectActiveTenantDeploymentsByTenantIdOperationImpl
        implements SelectActiveTenantDeploymentsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantDeploymentModelMapper tenantDeploymentModelMapper;

    @Override
    public Uni<List<TenantDeploymentModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, stage_id, version_id, created, modified, queue_id, deleted
                        from $schema.tab_tenant_deployment
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Tenant deployment",
                tenantDeploymentModelMapper::fromRow);
    }
}
