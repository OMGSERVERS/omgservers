package com.omgservers.service.module.tenant.impl.operation.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantStagePermissionModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantStagePermissionsByTenantIdOperationImpl
        implements SelectActiveTenantStagePermissionsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantStagePermissionModelMapper tenantStagePermissionModelMapper;

    @Override
    public Uni<List<TenantStagePermissionModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select 
                            id, idempotency_key, tenant_id, stage_id, created, modified, user_id, permission, deleted
                        from $schema.tab_tenant_stage_permission
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Tenant stage permission",
                tenantStagePermissionModelMapper::fromRow);
    }
}
