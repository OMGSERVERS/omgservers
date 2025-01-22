package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantProjectDetailsMethod {
    Uni<GetTenantProjectDetailsDeveloperResponse> execute(GetTenantProjectDetailsDeveloperRequest request);
}
