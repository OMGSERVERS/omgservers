package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantMethod {
    Uni<CreateTenantSupportResponse> execute(CreateTenantSupportRequest request);
}
