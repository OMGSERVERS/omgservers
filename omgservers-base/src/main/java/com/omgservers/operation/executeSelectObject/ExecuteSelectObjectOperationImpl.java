package com.omgservers.operation.executeSelectObject;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.module.system.factory.EventModelFactory;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.system.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.system.impl.operation.upsertLog.UpsertLogOperation;
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

import java.util.List;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ExecuteSelectObjectOperationImpl implements ExecuteSelectObjectOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public <T> Uni<T> executeSelectObject(final SqlConnection sqlConnection,
                                          final int shard,
                                          final String sql,
                                          final List<?> parameters,
                                          final String objectName,
                                          final Function<Row, T> objectMapper) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        final var object = objectMapper.apply(iterator.next());
                        log.debug("{} was selected, {}", objectName, object);
                        return object;
                    } else {
                        throw new ServerSideNotFoundException(String.format("%s was not found, sql=%s, parameters=%s",
                                objectName.toLowerCase(), preparedSql, parameters));
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }
}
