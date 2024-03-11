package com.omgservers.service.module.tenant.impl.operation.selectActiveTenantPermissionsByTenantId;

import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantPermissionModelMapper;
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
class SelectActiveTenantPermissionsByTenantIdOperationImpl implements SelectActiveTenantPermissionsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantPermissionModelMapper tenantPermissionModelMapper;

    @Override
    public Uni<List<TenantPermissionModel>> selectActiveTenantPermissionsByTenantId(final SqlConnection sqlConnection,
                                                                                    final int shard,
                                                                                    final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key,tenant_id, created, modified, user_id, permission, deleted
                        from $schema.tab_tenant_permission
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Tenant permission",
                tenantPermissionModelMapper::fromRow);
    }
}
