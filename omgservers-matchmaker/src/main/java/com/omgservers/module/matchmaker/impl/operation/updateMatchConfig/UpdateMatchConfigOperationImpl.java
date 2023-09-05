package com.omgservers.module.matchmaker.impl.operation.updateMatchConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.match.MatchConfigModel;
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
import java.time.Instant;
import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchConfigOperationImpl implements UpdateMatchConfigOperation {

    static private final String sql = """
            update $schema.tab_matchmaker_match
            set modified = $3, config = $4
            where matchmaker_id = $1 and id = $2
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateMatch(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long matchmakerId,
                                    final Long matchId,
                                    final MatchConfigModel config) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (matchmakerId == null) {
            throw new ServerSideBadRequestException("matchmakerId is null");
        }
        if (matchId == null) {
            throw new ServerSideBadRequestException("matchId is null");
        }
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }

        return updateObject(sqlConnection, shard, matchmakerId, matchId, config)
                .call(objectWasUpdated -> upsertEvent(objectWasUpdated, changeContext, sqlConnection, matchmakerId, matchId))
                .call(objectWasUpdated -> upsertLog(objectWasUpdated, changeContext, sqlConnection, matchmakerId, matchId))
                .invoke(objectWasUpdated -> {
                    if (objectWasUpdated) {
                        log.info("Match was updated, shard={}, matchmakerId={}, matchId={}",
                                shard, matchmakerId, matchId);
                    } else {
                        throw new ServerSideNotFoundException(String.format("match was not found, " +
                                "matchmakerId=%d, matchId=%d", matchmakerId, matchId));
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> updateObject(final SqlConnection sqlConnection,
                              final int shard,
                              final Long matchmakerId,
                              final Long matchId,
                              final MatchConfigModel config) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(config);
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.of(
                            matchmakerId,
                            matchId,
                            Instant.now().atOffset(ZoneOffset.UTC),
                            configString))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long matchmakerId,
                             final Long matchId) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasUpdated,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long matchmakerId,
                           final Long matchId) {
        if (objectWasUpdated) {
            final var changeLog = logModelFactory.create(String.format("Match was updated, " +
                    "matchmakerId=%d, matchId=%d", matchmakerId, matchId));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
