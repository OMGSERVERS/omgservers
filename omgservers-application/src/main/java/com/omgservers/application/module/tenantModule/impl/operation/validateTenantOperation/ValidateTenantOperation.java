package com.omgservers.application.module.tenantModule.impl.operation.validateTenantOperation;

import com.omgservers.model.tenant.TenantModel;

public interface ValidateTenantOperation {
    TenantModel validateTenant(TenantModel tenant);
}
