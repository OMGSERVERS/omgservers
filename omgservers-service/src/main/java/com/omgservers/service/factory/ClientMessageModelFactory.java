package com.omgservers.service.factory;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.message.MessageBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientMessageModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ClientMessageModel create(final Long clientId,
                                     final MessageQualifierEnum qualifier,
                                     final MessageBodyModel body) {
        final var id = generateIdOperation.generateId();
        return create(id, clientId, qualifier, body);
    }

    public ClientMessageModel create(final Long id,
                                     final Long clientId,
                                     final MessageQualifierEnum qualifier,
                                     final MessageBodyModel body) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var clientMessage = new ClientMessageModel();
        clientMessage.setId(id);
        clientMessage.setClientId(clientId);
        clientMessage.setCreated(now);
        clientMessage.setModified(now);
        clientMessage.setQualifier(qualifier);
        clientMessage.setBody(body);
        clientMessage.setDeleted(false);

        return clientMessage;
    }
}