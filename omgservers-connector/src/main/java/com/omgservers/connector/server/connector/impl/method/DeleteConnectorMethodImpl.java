package com.omgservers.connector.server.connector.impl.method;

import com.omgservers.connector.server.connector.dto.DeleteConnectorRequest;
import com.omgservers.connector.server.connector.dto.DeleteConnectorResponse;
import com.omgservers.connector.server.connector.impl.component.Connectors;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteConnectorMethodImpl implements DeleteConnectorMethod {

    final Connectors connectors;

    @Override
    public Uni<DeleteConnectorResponse> execute(final DeleteConnectorRequest request) {
        log.debug("{}", request);

        final var clientId = request.getConnectorConnection().getClientId();

        final var connector = connectors.deleteConnector(clientId);
        if (Objects.nonNull(connector)) {
            return Uni.createFrom().item(new DeleteConnectorResponse(Boolean.TRUE));
        } else {
            log.error("Connector for \"{}\" not found to be removed", clientId);
            return Uni.createFrom().item(new DeleteConnectorResponse(Boolean.FALSE));
        }
    }
}
