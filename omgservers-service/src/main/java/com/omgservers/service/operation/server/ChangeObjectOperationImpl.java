package com.omgservers.service.operation.server;

import com.omgservers.schema.model.log.LogModel;
import com.omgservers.service.event.EventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.server.event.operation.UpsertEventOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ChangeObjectOperationImpl implements ChangeObjectOperation {

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareSqlOperation prepareSqlOperation;
    final PrepareSlotSqlOperation prepareSlotSqlOperation;
    final UpsertEventOperation upsertEventOperation;

    final EventModelFactory eventModelFactory;

    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final String sql,
                                final List<?> parameters,
                                final Supplier<EventBodyModel> eventBodySupplier,
                                final Supplier<LogModel> logSupplier) {
        final var preparedSql = prepareSlotSqlOperation.execute(sql, slot);
        return executeQuery(changeContext, sqlConnection, preparedSql, parameters, eventBodySupplier, logSupplier);
    }

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final String sql,
                                final List<?> parameters,
                                final Supplier<EventBodyModel> eventBodySupplier,
                                final Supplier<LogModel> logSupplier) {
        final var preparedSql = prepareSqlOperation.execute(sql);
        return executeQuery(changeContext, sqlConnection, preparedSql, parameters, eventBodySupplier, logSupplier);
    }

    Uni<Boolean> executeQuery(final ChangeContext<?> changeContext,
                              final SqlConnection sqlConnection,
                              final String preparedSql,
                              final List<?> parameters,
                              final Supplier<EventBodyModel> eventBodySupplier,
                              final Supplier<LogModel> logSupplier) {
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(parameters))
                .map(rowSet -> rowSet.rowCount() > 0)
                .call(result -> upsertEvent(result, changeContext, sqlConnection, eventBodySupplier))
                .call(result -> upsertLog(result, changeContext, sqlConnection, logSupplier))
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException(preparedSql, (PgException) t));
    }

    Uni<Void> upsertEvent(final boolean result,
                          final ChangeContext<?> changeContext,
                          final SqlConnection sqlConnection,
                          final Supplier<EventBodyModel> eventBodySupplier) {
        if (result) {
            final var body = eventBodySupplier.get();
            if (Objects.nonNull(body)) {
                final var event = eventModelFactory.create(body);
                return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event)
                        .replaceWithVoid();
            }
        }
        return Uni.createFrom().voidItem();
    }

    Uni<Void> upsertLog(final boolean result,
                        final ChangeContext<?> changeContext,
                        final SqlConnection sqlConnection,
                        final Supplier<LogModel> logSupplier) {
        // TODO clean up method
        return Uni.createFrom().voidItem();
    }
}
