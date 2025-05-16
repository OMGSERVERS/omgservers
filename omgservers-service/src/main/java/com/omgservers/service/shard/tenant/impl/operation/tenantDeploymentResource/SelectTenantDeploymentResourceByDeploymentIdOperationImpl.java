package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.tenant.impl.mapper.TenantDeploymentResourceModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantDeploymentResourceByDeploymentIdOperationImpl implements SelectTenantDeploymentResourceByDeploymentIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantDeploymentResourceModelMapper tenantDeploymentResourceModelMapper;

    @Override
    public Uni<TenantDeploymentResourceModel> execute(final SqlConnection sqlConnection,
                                                      final int slot,
                                                      final Long tenantId,
                                                      final Long deploymentId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, tenant_id, stage_id, version_id, created, modified, deployment_id,
                            status, config, deleted
                        from $slot.tab_tenant_deployment_resource
                        where tenant_id = $1 and deployment_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, deploymentId),
                "Tenant deployment resource",
                tenantDeploymentResourceModelMapper::execute);
    }
}
