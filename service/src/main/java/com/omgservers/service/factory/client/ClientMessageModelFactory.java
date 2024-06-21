package com.omgservers.service.factory.client;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.message.MessageBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientMessageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientMessageModel create(final Long clientId,
                                     final MessageQualifierEnum qualifier,
                                     final MessageBodyModel body) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, clientId, qualifier, body, idempotencyKey);
    }

    public ClientMessageModel create(final Long clientId,
                                     final MessageQualifierEnum qualifier,
                                     final MessageBodyModel body,
                                     final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, clientId, qualifier, body, idempotencyKey);
    }

    public ClientMessageModel create(final Long id,
                                     final Long clientId,
                                     final MessageQualifierEnum qualifier,
                                     final MessageBodyModel body,
                                     final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var clientMessage = new ClientMessageModel();
        clientMessage.setId(id);
        clientMessage.setIdempotencyKey(idempotencyKey);
        clientMessage.setClientId(clientId);
        clientMessage.setCreated(now);
        clientMessage.setModified(now);
        clientMessage.setQualifier(qualifier);
        clientMessage.setBody(body);
        clientMessage.setDeleted(false);

        return clientMessage;
    }
}
