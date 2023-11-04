package com.omgservers.module.tenant.impl.service.tenantService.impl.method.deleteTenant;

import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<Void> deleteTenant(DeleteTenantRequest request);
}
