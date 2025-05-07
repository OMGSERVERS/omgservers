package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface AuthorizeTenantRequestOperation {
    Uni<TenantAuthorization> execute(String tenant,
                                     Long userId,
                                     TenantPermissionQualifierEnum permission);
}
