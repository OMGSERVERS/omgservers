package com.omgservers.base.impl.operation.updateEventStatusOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.event.EventStatusEnum;
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

    static private final String sql = """
            update internal.tab_event set status = $2
            where id = $1
            """;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateEventStatus(final SqlConnection sqlConnection,
                                          final Long id,
                                          final EventStatusEnum newStatus) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }
        if (newStatus == null) {
            throw new ServerSideBadRequestException("newStatus is null");
        }

        return updateQuery(sqlConnection, id, newStatus)
                .invoke(updated -> {
                    if (updated) {
                        log.info("Event was updated, id={}, newStatus={}", id, newStatus);
                    } else {
                        log.warn("Event wasn't found, id={}, newStatus={}", id, newStatus);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, id=%s, newStatus=%s", t.getMessage(), id, newStatus)));
    }

    Uni<Boolean> updateQuery(SqlConnection sqlConnection, Long id, EventStatusEnum status) {
        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(id, status.toString()))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
