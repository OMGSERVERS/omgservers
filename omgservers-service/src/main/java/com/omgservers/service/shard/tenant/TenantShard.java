package com.omgservers.service.shard.tenant;

import com.omgservers.service.shard.tenant.impl.service.tenantService.TenantService;

public interface TenantShard {

    TenantService getService();
}
