package com.omgservers.application.module.tenantModule.impl.operation.hasProjectPermissionOperation;

import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionEnum;
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
class HasProjectPermissionOperationImpl implements HasProjectPermissionOperation {

    static private final String sql = """
            select id
            from $schema.tab_project_permission
            where project_uuid = $1 and user_uuid = $2 and permission = $3
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> hasProjectPermission(final SqlConnection sqlConnection,
                                             final int shard,
                                             final UUID project,
                                             final UUID user,
                                             final ProjectPermissionEnum permission) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (project == null) {
            throw new IllegalArgumentException("project is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(project, user, permission))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
