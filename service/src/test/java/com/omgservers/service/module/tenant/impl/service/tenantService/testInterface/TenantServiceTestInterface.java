package com.omgservers.service.module.tenant.impl.service.tenantService.testInterface;

import com.omgservers.schema.module.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.DeleteTenantResponse;
import com.omgservers.schema.module.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.SyncTenantResponse;
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final TenantService tenantService;

    public SyncTenantResponse syncTenant(final SyncTenantRequest request) {
        return tenantService.syncTenant(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantResponse deleteTenant(DeleteTenantRequest request) {
        return tenantService.deleteTenant(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
