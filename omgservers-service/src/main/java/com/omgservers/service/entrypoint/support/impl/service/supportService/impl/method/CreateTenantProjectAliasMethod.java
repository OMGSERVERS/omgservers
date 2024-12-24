package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectAliasMethod {
    Uni<CreateTenantProjectAliasSupportResponse> execute(CreateTenantProjectAliasSupportRequest request);
}
