package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantProjectPermissionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantProjectPermissionModel create(final Long tenantId,
                                               final Long projectId,
                                               final Long userId,
                                               final TenantProjectPermissionQualifierEnum permissionQualifier) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, projectId, userId, permissionQualifier, idempotencyKey);
    }

    public TenantProjectPermissionModel create(final Long tenantId,
                                               final Long projectId,
                                               final Long userId,
                                               final TenantProjectPermissionQualifierEnum permissionQualifier,
                                               final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, projectId, userId, permissionQualifier, idempotencyKey);
    }

    public TenantProjectPermissionModel create(final Long id,
                                               final Long tenantId,
                                               final Long projectId,
                                               final Long userId,
                                               final TenantProjectPermissionQualifierEnum permissionQualifier,
                                               final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantProjectPermission = new TenantProjectPermissionModel();
        tenantProjectPermission.setId(id);
        tenantProjectPermission.setTenantId(tenantId);
        tenantProjectPermission.setProjectId(projectId);
        tenantProjectPermission.setCreated(now);
        tenantProjectPermission.setModified(now);
        tenantProjectPermission.setIdempotencyKey(idempotencyKey);
        tenantProjectPermission.setUserId(userId);
        tenantProjectPermission.setPermission(permissionQualifier);
        tenantProjectPermission.setDeleted(false);
        return tenantProjectPermission;
    }
}
