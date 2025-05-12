package com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient;

import java.net.URI;

public interface GetTenantModuleClientOperation {
    TenantModuleClient execute(URI uri);
}
