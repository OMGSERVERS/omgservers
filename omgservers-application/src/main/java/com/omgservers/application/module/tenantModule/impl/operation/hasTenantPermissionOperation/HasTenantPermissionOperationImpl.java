package com.omgservers.application.module.tenantModule.impl.operation.hasTenantPermissionOperation;

import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasTenantPermissionOperationImpl implements HasTenantPermissionOperation {

    static private final String sql = """
            select id
            from $schema.tab_tenant_permission
            where tenant_uuid = $1 and user_uuid = $2 and permission = $3
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> hasTenantPermission(final SqlConnection sqlConnection,
                                            final int shard,
                                            final UUID tenant,
                                            final UUID user,
                                            final TenantPermissionEnum permission) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (tenant == null) {
            throw new IllegalArgumentException("tenant is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(tenant, user, permission))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(selected -> {
                    if (selected) {
                        log.info("Tenant's permission was found, tenant={}, user={}, permission={}",
                                tenant, user, permission);
                    }
                });
    }
}
