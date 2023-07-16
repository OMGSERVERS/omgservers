package com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation;

import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEntity;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertStagePermissionOperationImpl implements UpsertStagePermissionOperation {

    static private final String sql = """
            insert into $schema.tab_stage_permission(stage_uuid, created, user_uuid, permission)
            values($1, $2, $3, $4)
            on conflict (stage_uuid, user_uuid, permission) do
            nothing
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> upsertStagePermission(final SqlConnection sqlConnection,
                                              final int shard,
                                              final StagePermissionEntity permission) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }

        return upsertQuery(sqlConnection, shard, permission)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Stage permission was inserted, permission={}", permission);
                    } else {
                        log.info("Stage permission was updated, permission={}", permission);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> {
                    final var pgException = (PgException) t;
                    final var code = pgException.getSqlState();
                    final var column = pgException.getColumn();
                    if (code.equals("23503")) {
                        // foreign_key_violation
                        return new ServerSideNotFoundException("stage was not found, permission=" + permission);
                    } else {
                        return new ServerSideConflictException("unhandled PgException, " + t.getMessage());
                    }
                });
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, StagePermissionEntity permission) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(
                        permission.getStage(),
                        permission.getCreated().atOffset(ZoneOffset.UTC),
                        permission.getUser(),
                        permission.getPermission()))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
