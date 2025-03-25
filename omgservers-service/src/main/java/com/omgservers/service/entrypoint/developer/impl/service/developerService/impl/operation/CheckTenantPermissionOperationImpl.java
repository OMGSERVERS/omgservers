package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CheckTenantPermissionOperationImpl implements CheckTenantPermissionOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long userId,
                             final TenantPermissionQualifierEnum permissionQualifier) {
        final var request = new VerifyTenantPermissionExistsRequest(tenantId, userId, permissionQualifier);
        return tenantShard.getService().execute(request)
                .map(VerifyTenantPermissionExistsResponse::getExists)
                .invoke(exists -> {
                    if (!exists) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, userId=%d, permissionQualifier=%s",
                                        tenantId, userId, permissionQualifier));
                    }
                })
                .replaceWithVoid();
    }
}
