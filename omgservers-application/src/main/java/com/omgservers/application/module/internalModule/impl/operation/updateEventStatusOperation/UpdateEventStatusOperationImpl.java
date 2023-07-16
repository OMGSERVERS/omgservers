package com.omgservers.application.module.internalModule.impl.operation.updateEventStatusOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.internalModule.model.event.EventStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateEventStatusOperationImpl implements UpdateEventStatusOperation {

    static private final String sql = """
            update internal.tab_event set status = $2
            where uuid = $1
            """;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateEventStatus(final SqlConnection sqlConnection,
                                          final UUID event,
                                          final EventStatusEnum newStatus) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (event == null) {
            throw new ServerSideBadRequestException("event is null");
        }
        if (newStatus == null) {
            throw new ServerSideBadRequestException("newStatus is null");
        }

        return updateQuery(sqlConnection, event, newStatus)
                .invoke(updated -> {
                    if (updated) {
                        log.info("Event was updated, event={}, newStatus={}", event, newStatus);
                    } else {
                        log.warn("Event wasn't found, event={}, newStatus={}", event, newStatus);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, event=%s, newStatus=%s", t.getMessage(), event, newStatus)));
    }

    Uni<Boolean> updateQuery(SqlConnection sqlConnection, UUID event, EventStatusEnum status) {
        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(event, status.toString()))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
