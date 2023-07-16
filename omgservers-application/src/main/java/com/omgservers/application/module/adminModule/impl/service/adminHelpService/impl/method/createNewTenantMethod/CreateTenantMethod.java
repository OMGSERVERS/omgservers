package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewTenantMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateTenantMethod {
    Uni<CreateTenantHelpResponse> createTenant(CreateTenantHelpRequest request);

    default CreateTenantHelpResponse createTenant(long timeout, CreateTenantHelpRequest request) {
        return createTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
