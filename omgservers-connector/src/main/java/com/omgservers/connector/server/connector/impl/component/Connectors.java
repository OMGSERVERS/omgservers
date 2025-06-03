package com.omgservers.connector.server.connector.impl.component;

import com.omgservers.connector.server.handler.component.ConnectorConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class Connectors {

    final Map<Long, Connector> indexByClientId;

    public Connectors() {
        indexByClientId = new HashMap<>();
    }

    public synchronized List<Connector> getAll() {
        return indexByClientId.values().stream().toList();
    }

    public synchronized Connector put(final Connector connector) {
        final var clientId = connector.getConnection().getClientId();
        return indexByClientId.put(clientId, connector);
    }

    public synchronized Connector deleteConnector(final Long clientId) {
        return indexByClientId.remove(clientId);
    }

    public synchronized Connector findConnector(final ConnectorConnection connectorConnection) {
        return indexByClientId.values().stream()
                .filter(connector -> connector.getConnection().equals(connectorConnection))
                .findFirst()
                .orElse(null);
    }
}
