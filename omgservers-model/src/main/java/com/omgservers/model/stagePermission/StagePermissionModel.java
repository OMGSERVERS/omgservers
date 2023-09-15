package com.omgservers.model.stagePermission;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StagePermissionModel {

    public static void validate(StagePermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Instant created;

    @NotNull
    Long userId;

    @NotNull
    StagePermissionEnum permission;
}
