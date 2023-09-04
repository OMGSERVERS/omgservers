package com.omgservers.module.matchmaker.impl.operation.selectMatchesByMatchmakerId;

import com.omgservers.model.match.MatchModel;
import com.omgservers.module.matchmaker.impl.mappers.MatchModelMapper;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchesByMatchmakerIdOperationImpl implements SelectMatchesByMatchmakerIdOperation {

    static private final String sql = """
            select id, matchmaker_id, created, modified, runtime_id, config
            from $schema.tab_matchmaker_match
            where matchmaker_id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;

    final MatchModelMapper matchModelMapper;

    @Override
    public Uni<List<MatchModel>> selectMatchesByMatchmakerId(final SqlConnection sqlConnection,
                                                             final int shard,
                                                             final Long matchmakerId) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (matchmakerId == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(matchmakerId))
                .map(RowSet::iterator)
                .map(iterator -> {
                    final List<MatchModel> matches = new ArrayList<MatchModel>();
                    while (iterator.hasNext()) {
                        try {
                            final var match = matchModelMapper.fromRow(iterator.next());
                            matches.add(match);
                        } catch (IOException e) {
                            log.error("Match's config can't be parsed, " +
                                    "matchmakerId={}, {}", matchmakerId, e.getMessage());
                        }
                    }
                    if (matches.size() > 0) {
                        log.info("Matches were selected, " +
                                "count={}, matchmakerId={}", matches.size(), matchmakerId);
                    } else {
                        log.info("Matches were not found, matchmakerId={}", matchmakerId);
                    }
                    return matches;
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }
}
