package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TenantStageMapper {

    TenantStageDto modelToDto(TenantStageModel model);

    @Mapping(source = "tenantStage", target = "stage")
    @Mapping(source = "tenantStagePermissions", target = "permissions")
    @Mapping(source = "tenantDeployments", target = "deployments")
    TenantStageDashboardDto dataToDashboard(TenantStageDataDto data);
}
