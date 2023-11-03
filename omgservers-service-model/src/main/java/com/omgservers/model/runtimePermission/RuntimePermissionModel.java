package com.omgservers.model.runtimePermission;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimePermissionModel {

    @NotNull
    Long id;

    @NotNull
    Long runtimeId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long userId;

    @NotNull
    RuntimePermissionEnum permission;

    @NotNull
    Boolean deleted;
}
