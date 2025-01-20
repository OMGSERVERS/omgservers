package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMatchmakerRefMethod {
    Uni<DeleteTenantMatchmakerRefResponse> execute(DeleteTenantMatchmakerRefRequest request);
}
