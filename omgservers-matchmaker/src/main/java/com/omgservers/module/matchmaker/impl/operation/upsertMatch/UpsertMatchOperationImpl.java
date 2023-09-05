package com.omgservers.module.matchmaker.impl.operation.upsertMatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.body.MatchCreatedEventBodyModel;
import com.omgservers.model.match.MatchModel;
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

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchOperationImpl implements UpsertMatchOperation {

    static private final String SQL = """
            insert into $schema.tab_matchmaker_match(id, matchmaker_id, created, modified, runtime_id, config)
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
    public Uni<Boolean> upsertMatch(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final MatchModel match) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (match == null) {
            throw new ServerSideBadRequestException("match is null");
        }

        return upsertObject(sqlConnection, shard, match)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, match))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, match))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Match was inserted, shard={}, match={}", shard, match);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, MatchModel match) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);
            var configString = objectMapper.writeValueAsString(match.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.of(
                            match.getId(),
                            match.getMatchmakerId(),
                            match.getCreated().atOffset(ZoneOffset.UTC),
                            match.getModified().atOffset(ZoneOffset.UTC),
                            match.getRuntimeId(),
                            configString))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final MatchModel match) {
        if (objectWasInserted) {
            final var body = new MatchCreatedEventBodyModel(match.getMatchmakerId(), match.getId());
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final MatchModel match) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Match was inserted, match=" + match);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
