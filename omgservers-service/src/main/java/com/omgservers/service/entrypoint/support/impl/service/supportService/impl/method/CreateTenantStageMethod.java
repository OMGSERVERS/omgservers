package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportResponse;
import io.smallrye.mutiny.Uni;

public interface CreateTenantStageMethod {
    Uni<CreateTenantStageSupportResponse> execute(CreateTenantStageSupportRequest request);
}
