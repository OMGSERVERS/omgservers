package com.omgservers.module.system.impl.operation.selectAllLogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.log.LogModel;
import com.omgservers.operation.executeSelectList.ExecuteSelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectAllLogsOperationImpl implements SelectAllLogsOperation {

    private static final String SQL = """
                        
            """;

    final ExecuteSelectListOperation executeSelectListOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<List<LogModel>> selectAllLogs(SqlConnection sqlConnection) {
        return executeSelectListOperation.executeSelectList(
                sqlConnection,
                0,
                """
                        select id, created, message
                        from internal.tab_log
                        """,
                List.of(),
                "Log",
                this::createLog);
    }

    LogModel createLog(Row row) {
        final var log = new LogModel();
        log.setId(row.getLong("id"));
        log.setCreated(row.getOffsetDateTime("created").toInstant());
        log.setMessage(row.getString("message"));
        return log;
    }
}
