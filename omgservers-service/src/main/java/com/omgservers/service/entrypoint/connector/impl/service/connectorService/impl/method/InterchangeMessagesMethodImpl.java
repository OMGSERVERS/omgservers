package com.omgservers.service.entrypoint.connector.impl.service.connectorService.impl.method;

import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import com.omgservers.schema.model.incomingMessage.IncomingMessageModel;
import com.omgservers.schema.shard.client.clientMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.client.clientMessage.InterchangeMessagesResponse;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InterchangeMessagesMethodImpl implements InterchangeMessagesMethod {

    final ClientShard clientShard;

    @Override
    public Uni<InterchangeMessagesConnectorResponse> execute(final InterchangeMessagesConnectorRequest request) {
        log.debug("Requested, {}", request);

        final var clientId = request.getClientId();
        final var messagesToHandle = request.getOutgoingMessages();
        final var consumedMessages = request.getConsumedMessages();

        final var interchangeMessagesRequest = new InterchangeMessagesRequest(clientId,
                messagesToHandle,
                consumedMessages);
        return clientShard.getService().execute(interchangeMessagesRequest)
                .map(InterchangeMessagesResponse::getIncomingMessages)
                .map(clientMessages -> {
                            final var incomingMessages = clientMessages.stream()
                                    .map(clientMessage -> new IncomingMessageModel(clientMessage.getId(),
                                            clientMessage.getQualifier(), clientMessage.getBody()))
                                    .toList();
                            return new InterchangeMessagesConnectorResponse(incomingMessages);
                        }
                );
    }
}
