package com.omgservers.schema.entrypoint.developer.dto.tenantStage;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStagePermissionDto {

    Long id;

    Instant created;

    Long userId;

    TenantStagePermissionQualifierEnum tenantStagePermission;
}
