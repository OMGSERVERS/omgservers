package com.omgservers.schema.entrypoint.developer.dto.tenant;

import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDetailsDto {

    TenantDto tenant;

    List<TenantPermissionDto> permissions;

    List<TenantProjectDto> projects;
}
