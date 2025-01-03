package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceAnonymousClient;

import java.net.URI;

public interface GetServiceAnonymousClientOperation {
    ServiceAnonymousModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
