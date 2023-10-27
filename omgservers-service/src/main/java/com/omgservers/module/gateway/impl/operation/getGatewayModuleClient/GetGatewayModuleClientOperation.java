package com.omgservers.module.gateway.impl.operation.getGatewayModuleClient;

import java.net.URI;

public interface GetGatewayModuleClientOperation {
    GatewayModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
