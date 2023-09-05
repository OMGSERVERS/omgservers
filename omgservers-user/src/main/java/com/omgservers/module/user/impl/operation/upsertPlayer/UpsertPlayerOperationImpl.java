package com.omgservers.module.user.impl.operation.upsertPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.player.PlayerModel;
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

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertPlayerOperationImpl implements UpsertPlayerOperation {

    static private final String sql = """
            insert into $schema.tab_user_player(id, user_id, created, modified, stage_id, config)
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

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPlayer(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final PlayerModel player) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (player == null) {
            throw new ServerSideBadRequestException("player is null");
        }

        return upsertObject(sqlConnection, shard, player)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, player))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, player))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Player was inserted, shard={}, player={}", shard, player);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, PlayerModel player) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(player.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            player.getId(),
                            player.getUserId(),
                            player.getCreated().atOffset(ZoneOffset.UTC),
                            player.getModified().atOffset(ZoneOffset.UTC),
                            player.getStageId(),
                            configString
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final PlayerModel player) {
        if (objectWasInserted) {
            final var body = new PlayerCreatedEventBodyModel(player.getUserId(), player.getStageId(), player.getId());
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final PlayerModel player) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Player was created, player=" + player);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
