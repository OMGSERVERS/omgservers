package com.omgservers.schema.entrypoint.developer.dto.tenantProject;

import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantVersion.TenantVersionProjectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantProjectDetailsDto {

    TenantProjectDto project;

    List<TenantProjectPermissionDto> permissions;

    List<TenantStageDto> stages;

    List<TenantVersionProjectionDto> versions;
}
