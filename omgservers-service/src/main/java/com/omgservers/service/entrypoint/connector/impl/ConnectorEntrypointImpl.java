package com.omgservers.service.entrypoint.connector.impl;

import com.omgservers.service.entrypoint.connector.ConnectorEntrypoint;
import com.omgservers.service.entrypoint.connector.impl.service.connectorService.ConnectorService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ConnectorEntrypointImpl implements ConnectorEntrypoint {

    final ConnectorService connectorService;

    @Override
    public ConnectorService getService() {
        return connectorService;
    }
}
