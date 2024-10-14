package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDto;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TenantMapper {

    TenantDto modelToDto(TenantModel model);

    @Mapping(source = "tenantPermissions", target = "permissions")
    @Mapping(source = "tenantProjects", target = "projects")
    TenantDashboardDto dataToDashboard(TenantDataDto data);
}
