package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.deleteTenant;

import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteTenantResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<DeleteTenantResponse> deleteTenant(DeleteTenantRequest request);
}
