package com.omgservers.module.runtime.impl.operation.selectNewCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
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
class SelectNewCommandsOperationImpl implements SelectNewCommandsOperation {

    static private final String sql = """
            select id, runtime_id, created, modified, qualifier, body, status
            from $schema.tab_runtime_command
            where runtime_id = $1 and status = $2
            order by id asc
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<List<RuntimeCommandModel>> selectNewCommands(final SqlConnection sqlConnection,
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
                    final var commands = new ArrayList<RuntimeCommandModel>();
                    while (iterator.hasNext()) {
                        final var command = createCommand(iterator.next());
                        commands.add(command);
                    }
                    if (commands.size() > 0) {
                        log.info("New commands were selected, count={}, commands={}", commands.size(), commands);
                    }
                    return commands;
                });
    }

    RuntimeCommandModel createCommand(Row row) {
        RuntimeCommandModel command = new RuntimeCommandModel();
        command.setId(row.getLong("id"));
        command.setRuntimeId(row.getLong("runtime_id"));
        command.setCreated(row.getOffsetDateTime("created").toInstant());
        command.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = RuntimeCommandQualifierEnum.valueOf(row.getString("qualifier"));
        command.setQualifier(qualifier);
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            command.setBody(body);
        } catch (IOException e) {
            log.error("command can't be parsed, id=" + command.getId(), e);
        }
        command.setStatus(RuntimeCommandStatusEnum.valueOf(row.getString("status")));
        return command;
    }
}
