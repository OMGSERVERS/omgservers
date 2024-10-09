package com.omgservers.service.module.tenant.impl;

import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TenantModuleImpl implements TenantModule {

    final TenantService tenantService;

    @Override
    public TenantService getService() {
        return tenantService;
    }

}
