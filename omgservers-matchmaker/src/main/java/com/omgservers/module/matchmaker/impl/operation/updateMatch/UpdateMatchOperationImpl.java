package com.omgservers.module.matchmaker.impl.operation.updateMatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.match.MatchModel;
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
class UpdateMatchOperationImpl implements UpdateMatchOperation {

    static private final String sql = """
            update $schema.tab_matchmaker_match
            set modified = $2, config = $3
            where id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> updateMatch(final SqlConnection sqlConnection,
                                 final int shard,
                                 final MatchModel match) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (match == null) {
            throw new ServerSideBadRequestException("match is null");
        }

        return updateQuery(sqlConnection, shard, match)
                .invoke(updated -> {
                    if (updated) {
                        log.info("Match was updated, shard={}, match={}", shard, match);
                    } else {
                        throw new ServerSideNotFoundException("match was not found, match=" + match);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t))
                .replaceWithVoid();
    }

    Uni<Boolean> updateQuery(SqlConnection sqlConnection, int shard, MatchModel match) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(match.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.of(
                            match.getId(),
                            match.getModified().atOffset(ZoneOffset.UTC),
                            configString))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
