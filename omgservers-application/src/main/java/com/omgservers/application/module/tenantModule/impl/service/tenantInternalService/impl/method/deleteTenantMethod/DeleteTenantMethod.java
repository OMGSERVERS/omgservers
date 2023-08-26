package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.deleteTenantMethod;

import com.omgservers.dto.tenantModule.DeleteTenantRoutedRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<Void> deleteTenant(DeleteTenantRoutedRequest request);
}
