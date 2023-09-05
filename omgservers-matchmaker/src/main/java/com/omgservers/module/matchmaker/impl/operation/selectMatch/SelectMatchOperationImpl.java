package com.omgservers.module.matchmaker.impl.operation.selectMatch;

import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideNotFoundException;
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

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectMatchOperationImpl implements SelectMatchOperation {

    private static final String SQL = """
            select id, matchmaker_id, created, modified, runtime_id, config
            from $schema.tab_matchmaker_match
            where id = $1
            limit 1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;

    final MatchModelMapper matchModelMapper;

    @Override
    public Uni<MatchModel> selectMatch(final SqlConnection sqlConnection,
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
                        try {
                            final var match = matchModelMapper.fromRow(iterator.next());
                            log.info("Match was found, match={}", match);
                            return match;
                        } catch (IOException e) {
                            throw new ServerSideConflictException("match can't be parsed, id=" + id, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("match was not found, id=" + id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }
}
