package com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient;

import java.net.URI;

public interface GetTenantModuleClientOperation {
    TenantModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
