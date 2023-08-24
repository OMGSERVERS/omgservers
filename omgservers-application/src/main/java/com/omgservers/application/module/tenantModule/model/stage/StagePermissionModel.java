package com.omgservers.application.module.tenantModule.model.stage;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StagePermissionModel {

    static public void validate(StagePermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    Long id;
    Long stageId;
    Instant created;
    Long userId;
    StagePermissionEnum permission;
}
