package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantRequest;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantMethodImpl implements DeleteTenantMethod {

    final TenantModule tenantModule;

    final GetIdByTenantOperation getIdByTenantOperation;
    final GenerateIdOperation generateIdOperation;

    final TenantModelFactory tenantModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteTenantSupportResponse> execute(final DeleteTenantSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var deleteTenantRequest = new DeleteTenantRequest(tenantId);
                    return tenantModule.getService().deleteTenant(deleteTenantRequest)
                            .map(deleteTenantResponse -> {
                                final var deleted = deleteTenantResponse.getDeleted();

                                if (deleted) {
                                    log.info("The tenant \"{}\" was deleted", tenantId);
                                }

                                return new DeleteTenantSupportResponse(deleted);
                            });
                });
    }
}
