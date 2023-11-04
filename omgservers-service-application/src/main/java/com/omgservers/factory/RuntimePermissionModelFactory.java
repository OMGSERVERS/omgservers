package com.omgservers.factory;

import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimePermissionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimePermissionModel create(final Long runtimeId,
                                         final Long userId,
                                         final RuntimePermissionEnum permission) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, userId, permission);
    }

    static public RuntimePermissionModel create(final Long id,
                                                final Long runtimeId,
                                                final Long userId,
                                                final RuntimePermissionEnum permission) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var permissionModel = new RuntimePermissionModel();
        permissionModel.setId(id);
        permissionModel.setRuntimeId(runtimeId);
        permissionModel.setCreated(now);
        permissionModel.setModified(now);
        permissionModel.setUserId(userId);
        permissionModel.setPermission(permission);
        permissionModel.setDeleted(false);
        return permissionModel;
    }

}
