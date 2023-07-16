package com.omgservers.application.module.tenantModule.model.stage;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StagePermissionEntity {

    static public StagePermissionEntity create(final UUID stage,
                                               final UUID user,
                                               final StagePermissionEnum permission) {
        Instant now = Instant.now();

        StagePermissionEntity model = new StagePermissionEntity();
        model.setStage(stage);
        model.setCreated(now);
        model.setUser(user);
        model.setPermission(permission);
        return model;
    }

    static public void validateTenantPermission(StagePermissionEntity permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    UUID stage;
    @ToString.Exclude
    Instant created;
    UUID user;
    StagePermissionEnum permission;
}
