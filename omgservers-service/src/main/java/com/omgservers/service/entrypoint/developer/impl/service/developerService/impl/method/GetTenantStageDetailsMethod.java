package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantStageDetailsMethod {
    Uni<GetStageDetailsDeveloperResponse> execute(GetStageDetailsDeveloperRequest request);
}
