package com.omgservers.service.shard.tenant.impl;

import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.tenant.impl.service.tenantService.TenantService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TenantShardImpl implements TenantShard {

    final TenantService tenantService;

    @Override
    public TenantService getService() {
        return tenantService;
    }

}
