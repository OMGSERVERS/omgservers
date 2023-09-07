package com.omgservers.module.tenant.impl.operation.hasTenantPermission;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.operation.executeHasObject.ExecuteHasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasTenantPermissionOperationImpl implements HasTenantPermissionOperation {

    private static final String SQL = """
            select id
            from $schema.tab_tenant_permission
            where tenant_id = $1 and user_id = $2 and permission = $3
            limit 1
            """;

    final ExecuteHasObjectOperation executeHasObjectOperation;

    @Override
    public Uni<Boolean> hasTenantPermission(final SqlConnection sqlConnection,
                                            final int shard,
                                            final Long tenantId,
                                            final Long userId,
                                            final TenantPermissionEnum permission) {
        return executeHasObjectOperation.executeHasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_tenant_permission
                        where tenant_id = $1 and user_id = $2 and permission = $3
                        limit 1
                        """,
                Arrays.asList(tenantId, userId, permission),
                "Tenant permission");
    }
}
