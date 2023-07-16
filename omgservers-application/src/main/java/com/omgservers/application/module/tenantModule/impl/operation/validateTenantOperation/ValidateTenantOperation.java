package com.omgservers.application.module.tenantModule.impl.operation.validateTenantOperation;

import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;

public interface ValidateTenantOperation {
    TenantModel validateTenant(TenantModel tenant);
}
