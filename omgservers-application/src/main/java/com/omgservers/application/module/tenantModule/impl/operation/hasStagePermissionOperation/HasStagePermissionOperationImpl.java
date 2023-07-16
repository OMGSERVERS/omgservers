package com.omgservers.application.module.tenantModule.impl.operation.hasStagePermissionOperation;

import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEnum;
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
class HasStagePermissionOperationImpl implements HasStagePermissionOperation {

    static private final String sql = """
            select id
            from $schema.tab_stage_permission
            where stage_uuid = $1 and user_uuid = $2 and permission = $3
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> hasStagePermission(final SqlConnection sqlConnection,
                                           final int shard,
                                           final UUID stage,
                                           final UUID user,
                                           final StagePermissionEnum permission) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (stage == null) {
            throw new IllegalArgumentException("stage is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(stage, user, permission))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(selected -> {
                    if (selected) {
                        log.info("Stage's permission was found, stage={}, user={}, permission={}",
                                stage, user, permission);
                    }
                });
    }
}
