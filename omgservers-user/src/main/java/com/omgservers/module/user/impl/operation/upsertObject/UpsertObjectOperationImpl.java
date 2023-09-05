package com.omgservers.module.user.impl.operation.upsertObject;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.object.ObjectModel;
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
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertObjectOperationImpl implements UpsertObjectOperation {

    static private final String sql = """
            insert into $schema.tab_user_object(id, player_id, created, modified, name, body)
            values($1, $2, $3, $4, $5, $6)
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
    public Uni<Boolean> upsertObject(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final ObjectModel object) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (userId == null) {
            throw new ServerSideBadRequestException("userId is null");
        }
        if (object == null) {
            throw new ServerSideBadRequestException("stage is null");
        }

        return upsertObject(sqlConnection, shard, object)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, userId, object))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, userId, object))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Object was inserted, shard={}, userId={}, object={}",
                                shard, userId, object);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, ObjectModel object) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        object.getId(),
                        object.getPlayerId(),
                        object.getCreated().atOffset(ZoneOffset.UTC),
                        object.getModified().atOffset(ZoneOffset.UTC),
                        object.getName(),
                        object.getBody()
                )))
                .map(rowSet -> rowSet.rowCount() > 0);
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long userId,
                             final ObjectModel object) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long userId,
                           final ObjectModel object) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create(String.format("Object was created, " +
                    "userId=%d, object=%s", userId, object));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
