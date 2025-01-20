package com.omgservers.service.shard.client.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientMessageModelMapper {

    final ObjectMapper objectMapper;

    public ClientMessageModel fromRow(final Row row) {
        final var clientMessage = new ClientMessageModel();
        clientMessage.setId(row.getLong("id"));
        clientMessage.setIdempotencyKey(row.getString("idempotency_key"));
        clientMessage.setClientId(row.getLong("client_id"));
        clientMessage.setCreated(row.getOffsetDateTime("created").toInstant());
        clientMessage.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = MessageQualifierEnum.valueOf(row.getString("qualifier"));
        clientMessage.setQualifier(qualifier);
        clientMessage.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            clientMessage.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "client message can't be parsed, clientMessage=" + clientMessage, e);
        }
        return clientMessage;
    }
}
