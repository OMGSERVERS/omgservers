package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
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
            final TenantPermissionQualifierEnum permission) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, userId, permission, idempotencyKey);
    }

    public TenantPermissionModel create(
            final Long tenantId,
            final Long userId,
            final TenantPermissionQualifierEnum permission,
            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, userId, permission, idempotencyKey);
    }

    static public TenantPermissionModel create(final Long id,
                                               final Long tenantId,
                                               final Long userId,
                                               final TenantPermissionQualifierEnum permission,
                                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantPermission = new TenantPermissionModel();
        tenantPermission.setId(id);
        tenantPermission.setTenantId(tenantId);
        tenantPermission.setCreated(now);
        tenantPermission.setModified(now);
        tenantPermission.setIdempotencyKey(idempotencyKey);
        tenantPermission.setUserId(userId);
        tenantPermission.setPermission(permission);
        tenantPermission.setDeleted(false);
        return tenantPermission;
    }

}
