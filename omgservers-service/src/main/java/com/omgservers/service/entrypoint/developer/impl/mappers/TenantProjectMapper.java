package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.module.tenant.tenantProject.dto.TenantProjectDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TenantProjectMapper {

    TenantProjectDto modelToDto(TenantProjectModel model);

    @Mapping(source = "tenantProject", target = "project")
    @Mapping(source = "tenantProjectPermissions", target = "permissions")
    @Mapping(source = "tenantStages", target = "stages")
    @Mapping(source = "tenantVersionProjections", target = "versions")
    TenantProjectDashboardDto dataToDashboard(TenantProjectDataDto data);
}
