package com.omgservers.module.runtime.impl.operation.selectNewRuntimeCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectNewRuntimeCommandsOperationImpl implements SelectNewRuntimeCommandsOperation {

    static private final String sql = """
            select id, runtime_id, created, modified, qualifier, body, status, step
            from $schema.tab_runtime_command
            where runtime_id = $1 and status = $2
            order by id asc
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<List<RuntimeCommandModel>> selectNewRuntimeCommands(final SqlConnection sqlConnection,
                                                                   final int shard,
                                                                   final Long runtimeId) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (runtimeId == null) {
            throw new IllegalArgumentException("runtimeId is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(runtimeId, RuntimeCommandStatusEnum.NEW))
                .map(RowSet::iterator)
                .map(iterator -> {
                    final var runtimeCommands = new ArrayList<RuntimeCommandModel>();
                    while (iterator.hasNext()) {
                        final var command = createRuntimeCommand(iterator.next());
                        runtimeCommands.add(command);
                    }
                    if (runtimeCommands.size() > 0) {
                        log.info("New runtime commands were selected, " +
                                "count={}, runtimeId={}", runtimeCommands.size(), runtimeId);
                    } else {
                        log.info("New runtime commands were not found, runtimeId={}", runtimeId);
                    }
                    return runtimeCommands;
                });
    }

    RuntimeCommandModel createRuntimeCommand(Row row) {
        RuntimeCommandModel runtimeCommand = new RuntimeCommandModel();
        runtimeCommand.setId(row.getLong("id"));
        runtimeCommand.setRuntimeId(row.getLong("runtime_id"));
        runtimeCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = RuntimeCommandQualifierEnum.valueOf(row.getString("qualifier"));
        runtimeCommand.setQualifier(qualifier);
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            runtimeCommand.setBody(body);
        } catch (IOException e) {
            log.error("runtime command can't be parsed, id=" + runtimeCommand.getId(), e);
        }
        runtimeCommand.setStatus(RuntimeCommandStatusEnum.valueOf(row.getString("status")));
        runtimeCommand.setStep(row.getLong("step"));
        return runtimeCommand;
    }
}
