package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantStagePermissionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TenantStagePermissionModel create(final Long tenantId,
                                             final Long stageId,
                                             final Long userId,
                                             final TenantStagePermissionQualifierEnum permission) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, stageId, userId, permission, idempotencyKey);
    }

    public TenantStagePermissionModel create(final Long tenantId,
                                             final Long stageId,
                                             final Long userId,
                                             final TenantStagePermissionQualifierEnum permission,
                                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, stageId, userId, permission, idempotencyKey);
    }

    public TenantStagePermissionModel create(final Long id,
                                             final Long tenantId,
                                             final Long stageId,
                                             final Long userId,
                                             final TenantStagePermissionQualifierEnum permission,
                                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tenantStagePermission = new TenantStagePermissionModel();
        tenantStagePermission.setId(id);
        tenantStagePermission.setTenantId(tenantId);
        tenantStagePermission.setStageId(stageId);
        tenantStagePermission.setCreated(now);
        tenantStagePermission.setModified(now);
        tenantStagePermission.setIdempotencyKey(idempotencyKey);
        tenantStagePermission.setUserId(userId);
        tenantStagePermission.setPermission(permission);
        tenantStagePermission.setDeleted(false);
        return tenantStagePermission;
    }
}
