package com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
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

    static private final String sql = """
            insert into $schema.tab_matchmaker_match(id, matchmaker_id, created, modified, runtime_id, config)
            values($1, $2, $3, $4, $5, $6)
            on conflict (id) do
            update set modified = $4, config = $6
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertMatch(final SqlConnection sqlConnection,
                                    final int shard,
                                    final MatchModel match) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (match == null) {
            throw new ServerSideBadRequestException("match is null");
        }

        return upsertQuery(sqlConnection, shard, match)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Match was inserted, shard={}, match={}", shard, match);
                    } else {
                        log.info("Match was updated, shard={}, match={}", shard, match);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, match=%s", t.getMessage(), match)));
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, MatchModel match) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(match.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.of(
                            match.getId(),
                            match.getMatchmakerId(),
                            match.getCreated().atOffset(ZoneOffset.UTC),
                            match.getModified().atOffset(ZoneOffset.UTC),
                            match.getRuntimeId(),
                            configString))
                    .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
