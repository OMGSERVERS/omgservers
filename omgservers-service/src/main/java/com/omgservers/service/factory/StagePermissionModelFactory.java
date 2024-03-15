package com.omgservers.service.factory;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StagePermissionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public StagePermissionModel create(final Long tenantId,
                                       final Long stageId,
                                       final Long userId,
                                       final StagePermissionEnum permission) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, stageId, userId, permission, idempotencyKey);
    }

    public StagePermissionModel create(final Long tenantId,
                                       final Long stageId,
                                       final Long userId,
                                       final StagePermissionEnum permission,
                                       final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, stageId, userId, permission, idempotencyKey);
    }

    public StagePermissionModel create(final Long id,
                                       final Long tenantId,
                                       final Long stageId,
                                       final Long userId,
                                       final StagePermissionEnum permission,
                                       final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var stagePermission = new StagePermissionModel();
        stagePermission.setId(id);
        stagePermission.setTenantId(tenantId);
        stagePermission.setStageId(stageId);
        stagePermission.setCreated(now);
        stagePermission.setModified(now);
        stagePermission.setIdempotencyKey(idempotencyKey);
        stagePermission.setUserId(userId);
        stagePermission.setPermission(permission);
        stagePermission.setDeleted(false);
        return stagePermission;
    }
}
