package com.omgservers.service.entrypoint.connector;

import com.omgservers.service.entrypoint.connector.impl.service.connectorService.ConnectorService;

public interface ConnectorEntrypoint {
    ConnectorService getService();
}
