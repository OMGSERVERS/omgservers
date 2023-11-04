package com.omgservers.service.factory;

import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantPermissionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantPermissionModel create(
            final Long tenantId,
            final Long userId,
            final TenantPermissionEnum permission) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, userId, permission);
    }

    static public TenantPermissionModel create(final Long id,
                                               final Long tenantId,
                                               final Long userId,
                                               final TenantPermissionEnum permission) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        TenantPermissionModel permissionModel = new TenantPermissionModel();
        permissionModel.setId(id);
        permissionModel.setTenantId(tenantId);
        permissionModel.setCreated(now);
        permissionModel.setUserId(userId);
        permissionModel.setPermission(permission);
        return permissionModel;
    }

}
