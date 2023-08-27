package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.deleteTenant;

import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<Void> deleteTenant(DeleteTenantShardedRequest request);
}
