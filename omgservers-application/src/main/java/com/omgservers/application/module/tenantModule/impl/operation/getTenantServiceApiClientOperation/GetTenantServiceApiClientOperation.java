package com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation;

import java.net.URI;

public interface GetTenantServiceApiClientOperation {
    TenantServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
