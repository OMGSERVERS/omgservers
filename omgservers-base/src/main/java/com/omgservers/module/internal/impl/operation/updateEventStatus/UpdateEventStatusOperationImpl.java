package com.omgservers.module.internal.impl.operation.updateEventStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateEventStatusOperationImpl implements UpdateEventStatusOperation {

    private static final String SQL = """
            update internal.tab_event set status = $2
            where id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateEventStatus(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final Long id,
                                          final EventStatusEnum newStatus) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }
        if (newStatus == null) {
            throw new ServerSideBadRequestException("newStatus is null");
        }

        return updateObject(sqlConnection, id, newStatus)
                .invoke(objectWasUpdated -> {
                    if (objectWasUpdated) {
                        log.info("Event was updated, id={}, newStatus={}", id, newStatus);
                    } else {
                        log.warn("Event wasn't found, id={}, newStatus={}", id, newStatus);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> updateObject(SqlConnection sqlConnection, Long id, EventStatusEnum status) {
        return sqlConnection.preparedQuery(SQL)
                .execute(Tuple.of(id, status.toString()))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
