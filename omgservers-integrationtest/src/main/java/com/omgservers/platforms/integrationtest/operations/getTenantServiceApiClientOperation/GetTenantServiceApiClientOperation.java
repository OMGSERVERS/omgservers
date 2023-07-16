package com.omgservers.platforms.integrationtest.operations.getTenantServiceApiClientOperation;

import java.net.URI;

public interface GetTenantServiceApiClientOperation {
    TenantServiceApiClient getClient(URI uri);
}
