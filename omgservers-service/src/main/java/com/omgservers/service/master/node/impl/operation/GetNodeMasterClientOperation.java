package com.omgservers.service.master.node.impl.operation;

import java.net.URI;

public interface GetNodeMasterClientOperation {
    NodeMasterClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
