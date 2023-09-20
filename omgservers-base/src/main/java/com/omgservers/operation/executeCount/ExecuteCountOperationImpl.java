package com.omgservers.operation.executeCount;

import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.system.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.system.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
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
class ExecuteCountOperationImpl implements ExecuteCountOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Integer> executeCount(final SqlConnection sqlConnection,
                                     final int shard,
                                     final String sql,
                                     final List<?> parameters,
                                     final String objectName) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(SqlResult::rowCount)
                .invoke(count -> {
                    if (count > 0) {
                        log.debug("{}/s was counted, count={}, parameters={}", objectName, count, parameters);
                    } else {
                        log.debug("{}/s was not found, parameters={}", objectName, parameters);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }
}
