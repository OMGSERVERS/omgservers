package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.schema.shard.tenant.tenant.DeleteTenantRequest;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantMethodImpl implements DeleteTenantMethod {

    final TenantShard tenantShard;

    final GetIdByTenantOperation getIdByTenantOperation;

    @Override
    public Uni<DeleteTenantSupportResponse> execute(final DeleteTenantSupportRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var deleteTenantRequest = new DeleteTenantRequest(tenantId);
                    return tenantShard.getService().execute(deleteTenantRequest)
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
