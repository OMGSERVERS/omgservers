package com.omgservers.module.tenant.impl.service.tenantShardedService.impl.method.deleteTenant;

import com.omgservers.dto.tenantModule.DeleteTenantShardRequest;
import io.smallrye.mutiny.Uni;

public interface DeleteTenantMethod {
    Uni<Void> deleteTenant(DeleteTenantShardRequest request);
}
