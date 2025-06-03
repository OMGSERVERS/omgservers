package com.omgservers.connector.server.connector.impl.method;

import com.omgservers.connector.server.connector.dto.CreateConnectorRequest;
import com.omgservers.connector.server.connector.dto.CreateConnectorResponse;
import com.omgservers.connector.server.connector.impl.component.Connector;
import com.omgservers.connector.server.connector.impl.component.Connectors;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateConnectorMethodImpl implements CreateConnectorMethod {

    final Connectors connectors;

    @Override
    public Uni<CreateConnectorResponse> execute(final CreateConnectorRequest request) {
        log.debug("{}", request);

        final var connectorConnection = request.getConnectorConnection();
        final var clientId = connectorConnection.getClientId();

        final var connector = new Connector(connectorConnection);

        if (Objects.isNull(connectors.put(connector))) {
            log.info("Connector for \"{}\" created", clientId);
            return Uni.createFrom().item(new CreateConnectorResponse(Boolean.TRUE));
        } else {
            log.warn("Connector for \"{}\" replaced", clientId);
            return Uni.createFrom().item(new CreateConnectorResponse(Boolean.FALSE));
        }
    }
}
