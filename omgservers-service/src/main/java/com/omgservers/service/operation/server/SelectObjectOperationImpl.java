package com.omgservers.service.operation.server;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.server.event.operation.UpsertEventOperation;
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
    final PrepareSqlOperation prepareSqlOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public <T> Uni<T> selectObject(final SqlConnection sqlConnection,
                                   final int shard,
                                   final String sql,
                                   final List<?> parameters,
                                   final String objectName,
                                   final Function<Row, T> objectMapper) {
        final var preparedSql = prepareShardSqlOperation.execute(sql, shard);
        return executeQuery(sqlConnection, preparedSql, parameters, objectName, objectMapper);
    }

    @Override
    public <T> Uni<T> selectObject(final SqlConnection sqlConnection,
                                   final String sql,
                                   final List<?> parameters,
                                   final String objectName,
                                   final Function<Row, T> objectMapper) {
        final var preparedSql = prepareSqlOperation.execute(sql);
        return executeQuery(sqlConnection, preparedSql, parameters, objectName, objectMapper);
    }

    <T> Uni<T> executeQuery(final SqlConnection sqlConnection,
                            final String preparedSql,
                            final List<?> parameters,
                            final String objectName,
                            final Function<Row, T> objectMapper) {
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        final var object = objectMapper.apply(iterator.next());
                        return object;
                    } else {
                        throw new ServerSideNotFoundException(
                                ExceptionQualifierEnum.OBJECT_NOT_FOUND,
                                String.format("%s was not found, sql=%s, parameters=%s",
                                        objectName.toLowerCase(),
                                        preparedSql.replaceAll(System.lineSeparator(), " "),
                                        parameters));
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }
}
