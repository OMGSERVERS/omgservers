package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantVersion.TenantVersionDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantVersion.TenantVersionDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface TenantVersionMapper {

    TenantVersionDto modelToDto(TenantVersionModel model);

    TenantVersionDashboardDto dataToDashboard(TenantVersionDataDto data);
}
