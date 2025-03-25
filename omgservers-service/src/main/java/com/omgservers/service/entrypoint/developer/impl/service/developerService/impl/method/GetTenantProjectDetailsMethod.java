package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantProjectDetailsMethod {
    Uni<GetProjectDetailsDeveloperResponse> execute(GetProjectDetailsDeveloperRequest request);
}
