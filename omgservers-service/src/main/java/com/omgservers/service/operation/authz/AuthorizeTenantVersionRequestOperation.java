package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface AuthorizeTenantVersionRequestOperation {
    Uni<TenantVersionAuthorization> execute(String tenant,
                                            String project,
                                            String version,
                                            Long userId,
                                            TenantProjectPermissionQualifierEnum permission);
}
