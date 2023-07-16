package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewTenantMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateTenantHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateTenantHelpResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.model.tenant.TenantConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.CreateTenantInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantMethodImpl implements CreateTenantMethod {

    final TenantModule tenantModule;

    @Override
    public Uni<CreateTenantHelpResponse> createTenant(final CreateTenantHelpRequest request) {
        CreateTenantHelpRequest.validate(request);

        final var uuid = UUID.randomUUID();
        final var tenant = TenantModel.create(uuid, TenantConfigModel.create());
        final var createTenantServiceRequest = new CreateTenantInternalRequest(tenant);
        return tenantModule.getTenantInternalService().createTenant(createTenantServiceRequest)
                .replaceWith(new CreateTenantHelpResponse(uuid));
    }
}
