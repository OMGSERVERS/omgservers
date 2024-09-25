package com.omgservers.schema.entrypoint.developer.dto.tenantProject;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantProjectPermissionDto {

    Long id;

    Instant created;

    Long userId;

    TenantProjectPermissionQualifierEnum tenantProjectPermission;
}
