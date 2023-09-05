package com.omgservers.module.tenant.impl.operation.upsertStagePermission;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
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
            insert into $schema.tab_tenant_stage_permission(id, stage_id, created, user_id, permission)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertStagePermission(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final Long tenantId,
                                              final StagePermissionModel permission) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (tenantId == null) {
            throw new ServerSideBadRequestException("tenantId is null");
        }
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }

        return upsertObject(sqlConnection, shard, permission)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, tenantId, permission))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, tenantId, permission))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Stage permission was inserted, permission={}", permission);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, StagePermissionModel permission) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(
                        permission.getId(),
                        permission.getStageId(),
                        permission.getCreated().atOffset(ZoneOffset.UTC),
                        permission.getUserId(),
                        permission.getPermission()))
                .map(rowSet -> rowSet.rowCount() > 0);
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long tenantId,
                             final StagePermissionModel permission) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long tenantId,
                           final StagePermissionModel permission) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create(String.format("Stage permission was inserted, " +
                    "tenantId=%d, permission=%s", tenantId, permission));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
