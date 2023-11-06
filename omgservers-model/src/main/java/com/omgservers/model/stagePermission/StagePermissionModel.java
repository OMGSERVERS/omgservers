package com.omgservers.model.stagePermission;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StagePermissionModel {

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long userId;

    @NotNull
    StagePermissionEnum permission;

    @NotNull
    Boolean deleted;
}
