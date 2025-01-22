package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperRequest;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionDetailsMethod {
    Uni<GetTenantVersionDetailsDeveloperResponse> execute(GetTenantVersionDetailsDeveloperRequest request);
}
