package com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.operation.server.VerifyObjectExistsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyTenantProjectPermissionExistsOperationImpl implements VerifyTenantProjectPermissionExistsOperation {

    final VerifyObjectExistsOperation verifyObjectExistsOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int slot,
                                final Long tenantId,
                                final Long tenantProjectId,
                                final Long userId,
                                final TenantProjectPermissionQualifierEnum tenantProjectPermissionQualifier) {
        return verifyObjectExistsOperation.execute(
                sqlConnection,
                slot,
                """
                        select id
                        from $slot.tab_tenant_project_permission
                        where
                            tenant_id = $1 and project_id = $2 and user_id = $3 and permission = $4 and deleted = false
                        limit 1
                        """,
                List.of(tenantId, tenantProjectId, userId, tenantProjectPermissionQualifier),
                "Tenant project permission");
    }
}
