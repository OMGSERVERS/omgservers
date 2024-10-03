package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface CreateTenantProjectPermissionOperation {
    Uni<TenantProjectPermissionModel> execute(final Long tenantId,
                                              final Long tenantProjectId,
                                              final Long userId,
                                              final TenantProjectPermissionQualifierEnum permissionQualifier);
}
