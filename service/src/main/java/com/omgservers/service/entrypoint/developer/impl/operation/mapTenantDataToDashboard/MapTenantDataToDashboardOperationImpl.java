package com.omgservers.service.entrypoint.developer.impl.operation.mapTenantDataToDashboard;

import com.omgservers.schema.entrypoint.developer.dto.TenantDashboardDto;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.ProjectMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.ProjectPermissionMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.StageMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.StagePermissionMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantMapper;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantPermissionMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class MapTenantDataToDashboardOperationImpl implements MapTenantDataToDashboardOperation {

    final ProjectPermissionMapper projectPermissionMapper;
    final TenantPermissionMapper tenantPermissionMapper;
    final StagePermissionMapper stagePermissionMapper;
    final ProjectMapper projectMapper;
    final TenantMapper tenantMapper;
    final StageMapper stageMapper;

    @Override
    public TenantDashboardDto mapTenantDataToDashboard(final TenantDataDto tenantData) {
        final var tenantDashboardDto = new TenantDashboardDto();

        // Dashboard tenant
        final var tenantDto = tenantMapper.modelToDto(tenantData.getTenant());
        tenantDashboardDto.setTenant(tenantDto);

        // Tenant permissions
        final var tenantPermissionsDto = tenantData.getTenantPermissions().stream()
                .map(tenantPermissionMapper::modelToDto)
                .toList();
        tenantDto.setPermissions(tenantPermissionsDto);

        // Tenant project
        final var projectDtoList = tenantData.getProjects().stream()
                .map(projectMapper::modelToDto)
                .peek(projectDto -> {
                    // Project permissions
                    final var projectPermissionsDto = tenantData.getProjectPermissions().stream()
                            .filter(projectPermissionModel -> projectPermissionModel.getProjectId()
                                    .equals(projectDto.getId()))
                            .map(projectPermissionMapper::modelToDto)
                            .toList();
                    projectDto.setPermissions(projectPermissionsDto);

                    // Project stages
                    final var stageDtoList = tenantData.getStages().stream()
                            .filter(stageModel -> stageModel.getProjectId().equals(projectDto.getId()))
                            .map(stageModel -> {
                                final var stageDto = stageMapper.modelToDto(stageModel);

                                // Stage permissions
                                final var stagePermissionDtoList = tenantData.getStagePermissions().stream()
                                        .filter(stagePermissionModel -> stagePermissionModel.getStageId()
                                                .equals(stageDto.getId()))
                                        .map(stagePermissionMapper::modelToDto)
                                        .toList();
                                stageDto.setPermissions(stagePermissionDtoList);

                                return stageDto;
                            })
                            .toList();
                    projectDto.setStages(stageDtoList);
                })
                .toList();
        tenantDto.setProjects(projectDtoList);


        return tenantDashboardDto;
    }
}
