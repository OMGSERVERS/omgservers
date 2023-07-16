package com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation;

import java.net.URI;

public interface GetUserServiceApiClientOperation {
    UserServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
