package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.TenantDeploymentDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.TenantDeploymentDto;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.tenant.tenantDeployment.dto.TenantDeploymentDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TenantDeploymentMapper {

    TenantDeploymentDto modelToDto(TenantDeploymentModel model);

    @Mapping(source = "tenantDeployment", target = "deployment")
    @Mapping(source = "tenantLobbyRefs", target = "lobbyRefs")
    @Mapping(source = "tenantMatchmakerRefs", target = "matchmakerRefs")
    TenantDeploymentDashboardDto dataToDashboard(TenantDeploymentDataDto data);
}
