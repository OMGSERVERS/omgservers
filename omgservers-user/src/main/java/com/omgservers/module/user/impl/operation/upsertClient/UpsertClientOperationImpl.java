package com.omgservers.module.user.impl.operation.upsertClient;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
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
class UpsertClientOperationImpl implements UpsertClientOperation {

    static private final String sql = """
            insert into $schema.tab_user_client(id, player_id, created, server, connection_id)
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
    public Uni<Boolean> upsertClient(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final ClientModel client) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (userId == null) {
            throw new ServerSideBadRequestException("userId is null");
        }
        if (client == null) {
            throw new ServerSideBadRequestException("client is null");
        }

        return upsertObject(sqlConnection, shard, client)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, userId, client))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, userId, client))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Client was inserted, shard={}, userId={}, client={}",
                                shard, userId, client);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, ClientModel client) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        client.getId(),
                        client.getPlayerId(),
                        client.getCreated().atOffset(ZoneOffset.UTC),
                        client.getServer().toString(),
                        client.getConnectionId()
                )))
                .map(rowSet -> rowSet.rowCount() > 0);
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long userId,
                             final ClientModel client) {
        if (objectWasInserted) {
            final var body = new ClientCreatedEventBodyModel(userId, client.getId());
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long userId,
                           final ClientModel client) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create(String.format("Client was inserted, " +
                    "userId=%d, client=%s", userId, client));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
