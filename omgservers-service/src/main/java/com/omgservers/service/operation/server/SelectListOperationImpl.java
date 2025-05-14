package com.omgservers.service.operation.server;

import com.omgservers.service.factory.system.EventModelFactory;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectListOperationImpl implements SelectListOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareSqlOperation prepareSqlOperation;
    final PrepareSlotSqlOperation prepareSlotSqlOperation;
    final UpsertEventOperation upsertEventOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public <T> Uni<List<T>> selectList(final SqlConnection sqlConnection,
                                       final int slot,
                                       final String sql,
                                       final List<?> parameters,
                                       final String objectName,
                                       final Function<Row, T> objectMapper) {
        final var preparedSql = prepareSlotSqlOperation.execute(sql, slot);
        return executeQuery(sqlConnection, preparedSql, parameters, objectName, objectMapper);
    }

    @Override
    public <T> Uni<List<T>> selectList(final SqlConnection sqlConnection,
                                       final String sql,
                                       final List<?> parameters,
                                       final String objectName,
                                       final Function<Row, T> objectMapper) {
        final var preparedSql = prepareSqlOperation.execute(sql);
        return executeQuery(sqlConnection, preparedSql, parameters, objectName, objectMapper);
    }

    <T> Uni<List<T>> executeQuery(final SqlConnection sqlConnection,
                                  final String preparedSql,
                                  final List<?> parameters,
                                  final String objectName,
                                  final Function<Row, T> objectMapper) {
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(RowSet::iterator)
                .map(iterator -> {
                    final List<T> objects = new ArrayList<T>();
                    while (iterator.hasNext()) {
                        try {
                            final var object = objectMapper.apply(iterator.next());
                            objects.add(object);
                        } catch (Exception e) {
                            log.error("Skip {}, {}", objectName.toLowerCase(), e.getMessage());
                        }
                    }
                    return objects;
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }
}
