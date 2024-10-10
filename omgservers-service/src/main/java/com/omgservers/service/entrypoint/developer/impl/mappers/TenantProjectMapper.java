package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.module.tenant.tenantProject.dto.TenantProjectDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface TenantProjectMapper {

    TenantProjectDto modelToDto(TenantProjectModel model);

    TenantProjectDashboardDto dataToDashboard(TenantProjectDataDto data);
}
