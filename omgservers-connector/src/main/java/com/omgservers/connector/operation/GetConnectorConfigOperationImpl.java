package com.omgservers.connector.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetConnectorConfigOperationImpl implements GetConnectorConfigOperation {

    final ConnectorConfig connectorConfig;

    public ConnectorConfig getConnectorConfig() {
        return connectorConfig;
    }
}
