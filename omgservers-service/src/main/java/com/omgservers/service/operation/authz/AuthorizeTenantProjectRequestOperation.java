package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface AuthorizeTenantProjectRequestOperation {
    Uni<TenantProjectAuthorization> execute(String tenant,
                                            String project,
                                            Long userId,
                                            TenantProjectPermissionQualifierEnum permission);
}
