package com.omgservers.service.shard.client.impl.operation.clientMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
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
class UpsertClientMessageOperationImpl implements UpsertClientMessageOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertClientMessage(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int slot,
                                            final ClientMessageModel clientMessage) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_client_message(
                            id, idempotency_key, client_id, created, modified, qualifier, body, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        clientMessage.getId(),
                        clientMessage.getIdempotencyKey(),
                        clientMessage.getClientId(),
                        clientMessage.getCreated().atOffset(ZoneOffset.UTC),
                        clientMessage.getModified().atOffset(ZoneOffset.UTC),
                        clientMessage.getQualifier(),
                        getBodyString(clientMessage),
                        clientMessage.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getBodyString(ClientMessageModel clientMessage) {
        try {
            return objectMapper.writeValueAsString(clientMessage.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
