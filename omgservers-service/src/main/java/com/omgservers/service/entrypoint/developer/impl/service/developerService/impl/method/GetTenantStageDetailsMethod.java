package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageDetailsMethod {
    Uni<GetTenantStageDetailsDeveloperResponse> execute(GetTenantStageDetailsDeveloperRequest request);
}
