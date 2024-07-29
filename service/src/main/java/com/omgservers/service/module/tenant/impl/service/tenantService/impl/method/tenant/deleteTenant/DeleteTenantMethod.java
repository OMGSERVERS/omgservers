package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.deleteTenant;

import com.omgservers.schema.module.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.DeleteTenantResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<DeleteTenantResponse> deleteTenant(DeleteTenantRequest request);
}
