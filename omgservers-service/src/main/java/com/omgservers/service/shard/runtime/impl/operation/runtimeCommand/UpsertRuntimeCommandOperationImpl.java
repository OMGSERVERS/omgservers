package com.omgservers.service.shard.runtime.impl.operation.runtimeCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRuntimeCommandOperationImpl implements UpsertRuntimeCommandOperation {

    final ChangeObjectOperation changeObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final RuntimeCommandModel runtimeCommand) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_runtime_command(
                            id, idempotency_key, runtime_id, created, modified, qualifier, body, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        runtimeCommand.getId(),
                        runtimeCommand.getIdempotencyKey(),
                        runtimeCommand.getRuntimeId(),
                        runtimeCommand.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeCommand.getModified().atOffset(ZoneOffset.UTC),
                        runtimeCommand.getQualifier(),
                        getBodyString(runtimeCommand),
                        runtimeCommand.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getBodyString(RuntimeCommandModel runtimeCommand) {
        try {
            return objectMapper.writeValueAsString(runtimeCommand.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
