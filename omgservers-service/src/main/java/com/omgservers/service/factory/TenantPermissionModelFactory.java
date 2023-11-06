package com.omgservers.service.factory;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantPermission = new TenantPermissionModel();
        tenantPermission.setId(id);
        tenantPermission.setTenantId(tenantId);
        tenantPermission.setCreated(now);
        tenantPermission.setModified(now);
        tenantPermission.setUserId(userId);
        tenantPermission.setPermission(permission);
        tenantPermission.setDeleted(false);
        return tenantPermission;
    }

}
