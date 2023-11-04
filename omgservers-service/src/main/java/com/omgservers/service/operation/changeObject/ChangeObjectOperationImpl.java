package com.omgservers.service.operation.changeObject;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.log.LogModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.service.module.system.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.service.operation.transformPgException.TransformPgExceptionOperation;
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
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    public Uni<Boolean> changeObject(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     String sql,
                                     List<?> parameters,
                                     Supplier<EventBodyModel> eventBodySupplier,
                                     Supplier<LogModel> logSupplier) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
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
        if (result) {
            final var changeLog = logSupplier.get();
            if (Objects.nonNull(changeLog)) {
                return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog)
                        .replaceWithVoid();
            }
        }
        return Uni.createFrom().voidItem();
    }
}
