package com.omgservers.service.module.client.impl.operation.clientMessage.upsertClientMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertClientMessage(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int shard,
                                            final ClientMessageModel clientMessage) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_client_message(
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
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
