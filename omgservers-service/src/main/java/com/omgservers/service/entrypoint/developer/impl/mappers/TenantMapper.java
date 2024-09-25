package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDto;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface TenantMapper {

    TenantDto mapToDto(TenantModel model);

    TenantDashboardDto dataToDashboard(TenantDataDto data);
}
