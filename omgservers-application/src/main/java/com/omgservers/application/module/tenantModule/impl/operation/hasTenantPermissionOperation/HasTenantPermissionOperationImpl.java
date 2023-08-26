package com.omgservers.application.module.tenantModule.impl.operation.hasTenantPermissionOperation;

import com.omgservers.base.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasTenantPermissionOperationImpl implements HasTenantPermissionOperation {

    static private final String sql = """
            select id
            from $schema.tab_tenant_permission
            where tenant_id = $1 and user_id = $2 and permission = $3
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> hasTenantPermission(final SqlConnection sqlConnection,
                                            final int shard,
                                            final Long tenantId,
                                            final Long userId,
                                            final TenantPermissionEnum permission) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId is null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(tenantId, userId, permission))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(selected -> {
                    if (selected) {
                        log.info("Tenant's permission was found, tenant={}, userId={}, permission={}",
                                tenantId, userId, permission);
                    }
                });
    }
}
