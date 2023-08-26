package com.omgservers.module.internal.impl.operation.deleteEvent;

import com.omgservers.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class DeleteEventOperationImpl implements DeleteEventOperation {

    static private final String sql = """
            delete from internal.tab_event where id = $1
            """;

    @Override
    public Uni<Boolean> deleteEvent(SqlConnection sqlConnection, Long id) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(id))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Event was deleted, id={}", id);
                    } else {
                        log.warn("Event was not found, skip operation, id={}", id);
                    }
                });
    }
}
