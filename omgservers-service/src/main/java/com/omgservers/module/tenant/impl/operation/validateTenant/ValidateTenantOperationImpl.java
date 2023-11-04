package com.omgservers.module.tenant.impl.operation.validateTenant;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.tenant.TenantModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
class ValidateTenantOperationImpl implements ValidateTenantOperation {

    @Override
    public TenantModel validateTenant(TenantModel tenant) {
        if (tenant == null) {
            throw new IllegalArgumentException("tenant is null");
        }

        var config = tenant.getConfig();

        Map<String, Boolean> results = new HashMap<>();

        // TODO: validate tenant

        var valid = results.values().stream().allMatch(Boolean.TRUE::equals);
        if (valid) {
            return tenant;
        } else {
            throw new ServerSideBadRequestException(String.format("bad tenant, tenant=%s", tenant));
        }
    }
}
