package com.omgservers.application.module.tenantModule.impl.operation.hasStagePermissionOperation;

import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasStagePermissionOperationImpl implements HasStagePermissionOperation {

    static private final String sql = """
            select id
            from $schema.tab_tenant_stage_permission
            where stage_id = $1 and user_id = $2 and permission = $3
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> hasStagePermission(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long stageId,
                                           final Long userId,
                                           final StagePermissionEnum permission) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (stageId == null) {
            throw new IllegalArgumentException("stageId is null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(stageId, userId, permission))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(selected -> {
                    if (selected) {
                        log.info("Stage's permission was found, stageId={}, userId={}, permission={}",
                                stageId, userId, permission);
                    }
                });
    }
}
