package com.omgservers.service.master.index.impl.operation;

import java.net.URI;

public interface GetIndexMasterClientOperation {
    IndexMasterClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
