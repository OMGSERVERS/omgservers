package com.omgservers.service.shard.runtime.impl.operation.runtimeMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.system.LogModelFactory;
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
class UpsertRuntimeMessageOperationImpl implements UpsertRuntimeMessageOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final RuntimeMessageModel runtimeMessage) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_runtime_message(
                            id, idempotency_key, runtime_id, created, modified, qualifier, body, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        runtimeMessage.getId(),
                        runtimeMessage.getIdempotencyKey(),
                        runtimeMessage.getRuntimeId(),
                        runtimeMessage.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeMessage.getModified().atOffset(ZoneOffset.UTC),
                        runtimeMessage.getQualifier(),
                        getBodyString(runtimeMessage),
                        runtimeMessage.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getBodyString(RuntimeMessageModel runtimeMessage) {
        try {
            return objectMapper.writeValueAsString(runtimeMessage.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
