package com.omgservers.schema.entrypoint.developer.dto;

import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantPermissionDto {

    Long id;

    Instant created;

    Long userId;

    TenantPermissionEnum permission;
}
