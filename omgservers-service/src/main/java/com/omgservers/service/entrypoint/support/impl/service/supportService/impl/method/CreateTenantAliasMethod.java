package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantAliasMethod {
    Uni<CreateTenantAliasSupportResponse> execute(CreateTenantAliasSupportRequest request);
}
