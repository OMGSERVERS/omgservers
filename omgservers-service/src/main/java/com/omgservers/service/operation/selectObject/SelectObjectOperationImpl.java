package com.omgservers.service.operation.selectObject;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.system.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.service.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.service.operation.transformPgException.TransformPgExceptionOperation;
import com.omgservers.service.operation.upsertEvent.UpsertEventOperation;
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
class SelectObjectOperationImpl implements SelectObjectOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public <T> Uni<T> selectObject(final SqlConnection sqlConnection,
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
                        return object;
                    } else {
                        throw new ServerSideNotFoundException(String.format("%s was not found, sql=%s, parameters=%s",
                                objectName.toLowerCase(),
                                preparedSql.replaceAll(System.lineSeparator(), " "),
                                parameters));
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }
}
