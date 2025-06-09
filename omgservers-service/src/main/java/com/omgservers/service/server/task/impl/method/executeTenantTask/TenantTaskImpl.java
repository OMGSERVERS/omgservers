package com.omgservers.service.server.task.impl.method.executeTenantTask;

import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.service.server.task.Task;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantTaskImpl implements Task<TenantTaskArguments> {

    final TenantShard tenantShard;

    public Uni<Boolean> execute(final TenantTaskArguments taskArguments) {
        final var tenantId = taskArguments.tenantId();

        return getTenant(tenantId)
                .replaceWith(Boolean.FALSE);
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantShard.getService().execute(request)
                .map(GetTenantResponse::getTenant);
    }
}
