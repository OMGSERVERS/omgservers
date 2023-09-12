package com.omgservers.module.tenant.factory;

import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
        return create(id, tenantId, stageId, userId, permission);
    }

    public StagePermissionModel create(final Long id,
                                       final Long tenantId,
                                       final Long stageId,
                                       final Long userId,
                                       final StagePermissionEnum permission) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        StagePermissionModel model = new StagePermissionModel();
        model.setId(id);
        model.setTenantId(tenantId);
        model.setStageId(stageId);
        model.setCreated(now);
        model.setUserId(userId);
        model.setPermission(permission);
        return model;
    }
}
