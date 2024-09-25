package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectMethod {
    Uni<CreateTenantProjectSupportResponse> execute(CreateTenantProjectSupportRequest request);
}
