package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantStageAliasMethod {
    Uni<CreateTenantStageAliasSupportResponse> execute(CreateTenantStageAliasSupportRequest request);
}
