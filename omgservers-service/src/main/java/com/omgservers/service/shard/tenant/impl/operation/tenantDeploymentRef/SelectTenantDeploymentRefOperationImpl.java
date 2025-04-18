package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectTenantDeploymentRefOperationImpl implements SelectTenantDeploymentRefOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantDeploymentRefModelMapper tenantDeploymentRefModelMapper;

    @Override
    public Uni<TenantDeploymentRefModel> execute(final SqlConnection sqlConnection,
                                                 final int slot,
                                                 final Long tenantId,
                                                 final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, tenant_id, stage_id, version_id, created, modified, deployment_id,
                            deleted
                        from $slot.tab_tenant_deployment_ref
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, id),
                "Tenant deployment ref",
                tenantDeploymentRefModelMapper::execute);
    }
}
