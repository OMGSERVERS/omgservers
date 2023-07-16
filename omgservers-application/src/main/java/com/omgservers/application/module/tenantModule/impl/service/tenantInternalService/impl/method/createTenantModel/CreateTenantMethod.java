package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.createTenantModel;

import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.CreateTenantInternalRequest;
import io.smallrye.mutiny.Uni;

public interface CreateTenantMethod {
    Uni<Void> createTenant(CreateTenantInternalRequest request);
}
