package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CheckTenantProjectPermissionOperationImpl implements CheckTenantProjectPermissionOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long tenantProjectId,
                             final Long userId,
                             final TenantProjectPermissionQualifierEnum permissionQualifier) {
        final var request = new VerifyTenantProjectPermissionExistsRequest(tenantId,
                tenantProjectId,
                userId,
                permissionQualifier);
        return tenantShard.getService().execute(request)
                .map(VerifyTenantProjectPermissionExistsResponse::getExists)
                .invoke(exists -> {
                    if (!exists) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, tenantProjectId=%d, userId=%d, permissionQualifier=%s",
                                        tenantId, tenantProjectId, userId, permissionQualifier));
                    }
                })
                .replaceWithVoid();
    }
}
