package com.omgservers.module.matchmaker.impl.operation.selectMatchClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchClientOperationImpl implements SelectMatchClientOperation {

    private static final String SQL = """
            select id, matchmaker_id, match_id, created, modified, user_id, client_id
            from $schema.tab_matchmaker_match_client
            where id = $1
            limit 1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<MatchClientModel> selectMatchClient(final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        final var matchClient = createMatchClient(iterator.next());
                        log.info("Match client was found, matchClient={}", matchClient);
                        return matchClient;
                    } else {
                        throw new ServerSideNotFoundException("match client was not found, id=" + id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    MatchClientModel createMatchClient(Row row) {
        MatchClientModel matchClient = new MatchClientModel();
        matchClient.setId(row.getLong("id"));
        matchClient.setMatchmakerId(row.getLong("matchmaker_id"));
        matchClient.setMatchId(row.getLong("match_id"));
        matchClient.setCreated(row.getOffsetDateTime("created").toInstant());
        matchClient.setModified(row.getOffsetDateTime("modified").toInstant());
        matchClient.setUserId(row.getLong("user_id"));
        matchClient.setClientId(row.getLong("client_id"));
        return matchClient;
    }
}
