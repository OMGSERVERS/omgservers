package com.omgservers.module.runtime.impl.operation.selectNewRuntimeCommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.RuntimeCommandStatusEnum;
import com.omgservers.operation.executeSelectList.ExecuteSelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectNewRuntimeCommandsOperationImpl implements SelectNewRuntimeCommandsOperation {

    final ExecuteSelectListOperation executeSelectListOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<List<RuntimeCommandModel>> selectNewRuntimeCommands(final SqlConnection sqlConnection,
                                                                   final int shard,
                                                                   final Long runtimeId) {
        return executeSelectListOperation.executeSelectList(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, qualifier, body, status, step
                        from $schema.tab_runtime_command
                        where runtime_id = $1 and status = $2
                        order by id asc
                        """,
                Arrays.asList(runtimeId, RuntimeCommandStatusEnum.NEW),
                "Runtime command",
                this::createRuntimeCommand);
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
            throw new ServerSideConflictException("runtime command can't be parsed, runtimeCommand=" + runtimeCommand, e);
        }
        runtimeCommand.setStatus(RuntimeCommandStatusEnum.valueOf(row.getString("status")));
        runtimeCommand.setStep(row.getLong("step"));
        return runtimeCommand;
    }
}
