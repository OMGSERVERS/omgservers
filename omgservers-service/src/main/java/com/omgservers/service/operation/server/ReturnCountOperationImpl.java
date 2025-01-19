package com.omgservers.service.operation.server;

import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.SqlResult;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ReturnCountOperationImpl implements ReturnCountOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Integer> returnCount(final SqlConnection sqlConnection,
                                    final int shard,
                                    final String sql,
                                    final List<?> parameters) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(SqlResult::rowCount)
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }
}
