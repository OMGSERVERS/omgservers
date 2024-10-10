package com.omgservers.schema.entrypoint.developer.dto.tenant;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantPermissionDto {

    Long id;

    Long tenantId;

    Instant created;

    Long userId;

    TenantPermissionQualifierEnum permission;
}
