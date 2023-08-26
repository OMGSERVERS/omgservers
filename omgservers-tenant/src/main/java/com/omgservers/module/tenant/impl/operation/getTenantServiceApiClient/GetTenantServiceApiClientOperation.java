package com.omgservers.module.tenant.impl.operation.getTenantServiceApiClient;

import java.net.URI;

public interface GetTenantServiceApiClientOperation {
    TenantServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
