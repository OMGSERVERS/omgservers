package com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRuntimeCommandOperationImpl implements UpsertRuntimeCommandOperation {

    final ExecuteChangeOperation executeChangeOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimeCommand(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final RuntimeCommandModel runtimeCommand) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_command(
                            id, runtime_id, created, modified, qualifier, body, status, step)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        runtimeCommand.getId(),
                        runtimeCommand.getRuntimeId(),
                        runtimeCommand.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeCommand.getModified().atOffset(ZoneOffset.UTC),
                        runtimeCommand.getQualifier(),
                        getBodyString(runtimeCommand),
                        runtimeCommand.getStatus(),
                        runtimeCommand.getStep()
                ),
                () -> null,
                () -> logModelFactory.create("Runtime command was inserted, runtimeCommand=" + runtimeCommand)
        );
    }

    String getBodyString(RuntimeCommandModel runtimeCommand) {
        try {
            return objectMapper.writeValueAsString(runtimeCommand.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
