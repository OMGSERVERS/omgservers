package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface GetTenantDetailsMethod {
    Uni<GetTenantDetailsDeveloperResponse> execute(GetTenantDetailsDeveloperRequest request);
}
