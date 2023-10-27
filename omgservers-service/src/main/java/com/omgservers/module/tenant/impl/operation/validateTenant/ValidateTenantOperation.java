package com.omgservers.module.tenant.impl.operation.validateTenant;

import com.omgservers.model.tenant.TenantModel;

public interface ValidateTenantOperation {
    TenantModel validateTenant(TenantModel tenant);
}
