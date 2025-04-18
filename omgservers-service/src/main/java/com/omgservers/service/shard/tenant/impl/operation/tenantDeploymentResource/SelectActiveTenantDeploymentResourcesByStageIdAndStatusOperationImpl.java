package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.service.operation.server.SelectListOperation;
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
class SelectActiveTenantDeploymentResourcesByStageIdAndStatusOperationImpl
        implements SelectActiveTenantDeploymentResourcesByStageIdAndStatusOperation {

    final SelectListOperation selectListOperation;

    final TenantDeploymentResourceModelMapper tenantDeploymentResourceModelMapper;

    @Override
    public Uni<List<TenantDeploymentResourceModel>> execute(final SqlConnection sqlConnection,
                                                            final int slot,
                                                            final Long tenantId,
                                                            final Long stageId,
                                                            final TenantDeploymentResourceStatusEnum status) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, tenant_id, stage_id, version_id, created, modified, deployment_id, 
                            status, deleted
                        from $slot.tab_tenant_deployment_resource
                        where tenant_id = $1 and stage_id = $2 and status = $3 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, stageId, status),
                "Tenant deployment resource",
                tenantDeploymentResourceModelMapper::execute);
    }
}
