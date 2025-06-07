package com.omgservers.connector.server.connector.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.connector.operation.GetConnectorConfigOperation;
import com.omgservers.connector.operation.GetServiceClientOperation;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesRequest;
import com.omgservers.connector.server.connector.dto.InterchangeMessagesResponse;
import com.omgservers.connector.server.connector.impl.component.Connector;
import com.omgservers.connector.server.connector.impl.component.Connectors;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import com.omgservers.schema.model.incomingMessage.IncomingMessageModel;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InterchangeMessagesMethodImpl implements InterchangeMessagesMethod {

    final GetConnectorConfigOperation getConnectorConfigOperation;
    final GetServiceClientOperation getServiceClientOperation;

    final ObjectMapper objectMapper;
    final Connectors connectors;

    @Override
    public Uni<InterchangeMessagesResponse> execute(final InterchangeMessagesRequest request) {
        log.debug("{}", request);

        final var serviceUri = getConnectorConfigOperation.getConnectorConfig().serviceUri();
        final var allConnectors = connectors.getAll();

        return Multi.createFrom().iterable(allConnectors)
                .onItem().transformToUniAndMerge(connector -> {
                    final var serviceClient = getServiceClientOperation.execute(serviceUri);

                    final var clientId = connector.getConnection().getClientId();
                    final var outgoingMessages = connector.pullOutgoingMessages();
                    final var consumerMessages = connector.pullConsumedMessages();
                    final var interchangeMessagesConnectorRequest = new InterchangeMessagesConnectorRequest(clientId,
                            outgoingMessages,
                            consumerMessages);

                    return serviceClient.execute(interchangeMessagesConnectorRequest)
                            .map(InterchangeMessagesConnectorResponse::getIncomingMessages)
                            .flatMap(incomingMessages ->
                                    transferIncomingMessages(connector, incomingMessages))
                            .onFailure()
                            .recoverWithUni(t -> {
                                log.warn("Failed to interchange messages for \"{}\", {}:{}",
                                        connector.getConnection().getClientId(),
                                        t.getClass().getSimpleName(), t.getMessage());
                                return Uni.createFrom().voidItem();
                            });
                })
                .collect().asList()
                .replaceWithVoid()
                .replaceWith(new InterchangeMessagesResponse(Boolean.TRUE));
    }

    Uni<Void> transferIncomingMessages(final Connector connector,
                                       final List<IncomingMessageModel> incomingMessages) {
        return Multi.createFrom().iterable(incomingMessages)
                .onItem().transformToUniAndConcatenate(incomingMessage -> {
                    try {
                        final var messageString = objectMapper.writeValueAsString(incomingMessage);
                        return connector.getConnection().sendText(messageString)
                                .invoke(voidItem -> connector.addConsumedMessage(incomingMessage.getId()));
                    } catch (IOException e) {
                        log.warn("Failed to transfer incoming message, {}, {}:{}",
                                connector,
                                e.getClass().getSimpleName(),
                                e.getMessage());
                        return Uni.createFrom().voidItem();
                    }
                })
                .collect().asList()
                .replaceWithVoid();
    }
}
