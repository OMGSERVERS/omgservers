package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.TenantDeploymentDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.TenantDeploymentDto;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.tenant.tenantDeployment.dto.TenantDeploymentDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface TenantDeploymentMapper {

    TenantDeploymentDto modelToDto(TenantDeploymentModel model);

    TenantDeploymentDashboardDto dataToDashboard(TenantDeploymentDataDto data);
}
