package com.omgservers.connector.entrypoint;

import com.omgservers.connector.entrypoint.impl.service.entrypointService.EntrypointService;

public interface ConnectorEntrypoint {

    EntrypointService getEntrypointService();
}
