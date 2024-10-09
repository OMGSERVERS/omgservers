package com.omgservers.service.module.tenant;

import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;

public interface TenantModule {

    TenantService getService();
}
