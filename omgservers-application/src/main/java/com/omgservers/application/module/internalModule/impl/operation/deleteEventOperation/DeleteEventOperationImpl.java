package com.omgservers.application.module.internalModule.impl.operation.deleteEventOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
class DeleteEventOperationImpl implements DeleteEventOperation {

    static private final String sql = """
            delete from internal.tab_event where uuid = $1
            """;

    @Override
    public Uni<Boolean> deleteEvent(SqlConnection sqlConnection, UUID uuid) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(uuid))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Event was deleted, uuid={}", uuid);
                    } else {
                        log.warn("Event was not found, skip operation, uuid={}", uuid);
                    }
                });
    }
}
