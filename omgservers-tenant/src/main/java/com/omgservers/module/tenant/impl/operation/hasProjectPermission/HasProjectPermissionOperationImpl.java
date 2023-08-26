package com.omgservers.module.tenant.impl.operation.hasProjectPermission;

import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasProjectPermissionOperationImpl implements HasProjectPermissionOperation {

    static private final String sql = """
            select id
            from $schema.tab_tenant_project_permission
            where project_id = $1 and user_id = $2 and permission = $3
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> hasProjectPermission(final SqlConnection sqlConnection,
                                             final int shard,
                                             final Long projectId,
                                             final Long userId,
                                             final ProjectPermissionEnum permission) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (projectId == null) {
            throw new IllegalArgumentException("projectId is null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(projectId, userId, permission))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
