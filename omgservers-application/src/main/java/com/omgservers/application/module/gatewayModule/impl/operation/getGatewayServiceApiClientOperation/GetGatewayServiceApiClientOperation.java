package com.omgservers.application.module.gatewayModule.impl.operation.getGatewayServiceApiClientOperation;

import java.net.URI;

public interface GetGatewayServiceApiClientOperation {
    GatewayServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
