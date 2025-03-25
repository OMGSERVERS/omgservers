package com.omgservers.service.operation.client;

import com.omgservers.schema.message.body.MessageProducedMessageBodyDto;
import com.omgservers.schema.module.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.module.client.clientMessage.SyncClientMessageResponse;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateMessageProducedClientMessageOperationImpl implements CreateMessageProducedClientMessageOperation {

    final ClientShard clientShard;

    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public Uni<Boolean> execute(final Long clientId,
                                final Object message) {
        final var messageBody = new MessageProducedMessageBodyDto(message);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                messageBody);

        final var request = new SyncClientMessageRequest(clientMessage);
        return clientShard.getService().execute(request)
                .map(SyncClientMessageResponse::getCreated)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, clientId={}, {}:{}",
                            clientId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }
}
