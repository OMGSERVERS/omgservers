package com.omgservers.dispatcher.operation.getServiceDispatcherEntrypointClient;

import java.net.URI;

public interface GetServiceDispatcherEntrypointClientOperation {
    ServiceDispatcherEntrypointModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
