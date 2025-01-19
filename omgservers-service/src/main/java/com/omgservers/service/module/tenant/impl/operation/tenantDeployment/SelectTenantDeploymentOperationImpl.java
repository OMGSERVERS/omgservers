package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantDeploymentModelMapper;
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
class SelectTenantDeploymentOperationImpl implements SelectTenantDeploymentOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantDeploymentModelMapper tenantDeploymentModelMapper;

    @Override
    public Uni<TenantDeploymentModel> execute(final SqlConnection sqlConnection,
                                              final int shard,
                                              final Long tenantId,
                                              final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, stage_id, version_id, created, modified, queue_id, deleted
                        from $schema.tab_tenant_deployment
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant deployment",
                tenantDeploymentModelMapper::fromRow);
    }
}
