package com.omgservers.schema.entrypoint.developer.dto;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StagePermissionDto {

    Long id;

    Instant created;

    Long userId;

    TenantStagePermissionEnum permission;
}
