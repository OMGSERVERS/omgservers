package com.omgservers.service.module.tenant.impl.operation.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.service.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class VerifyTenantPermissionExistsOperationImpl implements VerifyTenantPermissionExistsOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long tenantId,
                                final Long userId,
                                final TenantPermissionQualifierEnum tenantPermissionQualifier) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_tenant_permission
                        where tenant_id = $1 and user_id = $2 and permission = $3 and deleted = false
                        limit 1
                        """,
                List.of(tenantId, userId, tenantPermissionQualifier),
                "Tenant permission");
    }
}
