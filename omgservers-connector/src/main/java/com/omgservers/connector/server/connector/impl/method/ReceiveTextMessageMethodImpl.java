package com.omgservers.connector.server.connector.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageRequest;
import com.omgservers.connector.server.connector.dto.ReceiveTextMessageResponse;
import com.omgservers.connector.server.connector.impl.component.Connectors;
import com.omgservers.schema.message.MessageModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ReceiveTextMessageMethodImpl implements ReceiveTextMessageMethod {

    final Connectors connectors;

    final ObjectMapper objectMapper;

    @Override
    public Uni<ReceiveTextMessageResponse> execute(final ReceiveTextMessageRequest request) {
        log.debug("{}", request);

        final var connectorConnection = request.getConnectorConnection();
        final var connector = connectors.findConnector(connectorConnection);
        if (Objects.nonNull(connector)) {
            final var message = request.getMessage();
            try {
                final var outgoingMessage = objectMapper.readValue(message, MessageModel.class);
                connector.addOutgoingMessage(outgoingMessage);

                log.info("Received from \"{}\", \"{}\"", connector.getConnection().getClientId(), outgoingMessage);
                return Uni.createFrom().item(new ReceiveTextMessageResponse(Boolean.TRUE));
            } catch (IOException e) {
                log.warn("Failed to parse message from \"{}\", {}:{}",
                        connector.getConnection().getClientId(),
                        e.getClass().getSimpleName(), e.getMessage());
                return Uni.createFrom().item(new ReceiveTextMessageResponse(Boolean.FALSE));
            }
        } else {
            log.error("Failed to find connector for \"{}\"", connectorConnection.getClientId());
            return Uni.createFrom().item(new ReceiveTextMessageResponse(Boolean.FALSE));
        }
    }
}
