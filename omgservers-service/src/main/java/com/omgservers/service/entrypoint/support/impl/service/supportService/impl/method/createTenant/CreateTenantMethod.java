package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createTenant;

import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantMethod {
    Uni<CreateTenantSupportResponse> createTenant(CreateTenantSupportRequest request);
}
