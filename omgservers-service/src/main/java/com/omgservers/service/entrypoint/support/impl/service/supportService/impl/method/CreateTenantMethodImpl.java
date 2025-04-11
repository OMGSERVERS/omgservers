package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.model.tenant.TenantConfigDto;
import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.tenant.TenantShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantMethodImpl implements CreateTenantMethod {

    final TenantShard tenantShard;

    final GenerateIdOperation generateIdOperation;

    final TenantModelFactory tenantModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantSupportResponse> execute(final CreateTenantSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = tenantModelFactory.create(TenantConfigDto.create());
        final var syncTenantInternalRequest = new SyncTenantRequest(tenant);
        return tenantShard.getService().execute(syncTenantInternalRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("New tenant \"{}\" created", tenant.getId());
                    }
                })
                .replaceWith(new CreateTenantSupportResponse(tenant.getId()));
    }
}
