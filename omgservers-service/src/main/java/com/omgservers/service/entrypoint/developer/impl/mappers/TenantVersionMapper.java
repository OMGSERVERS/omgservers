package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantVersion.TenantVersionDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantVersion.TenantVersionDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TenantVersionMapper {

    TenantVersionDto modelToDto(TenantVersionModel model);

    @Mapping(source = "tenantVersion", target = "version")
    @Mapping(source = "tenantBuildRequests", target = "buildRequests")
    @Mapping(source = "tenantImages", target = "images")
    TenantVersionDashboardDto dataToDashboard(TenantVersionDataDto data);
}
