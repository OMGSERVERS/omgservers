package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperRequest;
import io.smallrye.mutiny.Uni;

public interface GetTenantVersionDetailsMethod {
    Uni<GetVersionDetailsDeveloperResponse> execute(GetVersionDetailsDeveloperRequest request);
}
