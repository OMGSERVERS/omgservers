package com.omgservers.schema.entrypoint.developer.dto.tenant;

import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDashboardDto {

    TenantDto tenant;

    List<TenantPermissionDto> tenantPermissions;

    List<TenantProjectDto> tenantProjects;
}
