package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface TenantStageMapper {

    TenantStageDto mapToDto(TenantStageModel model);

    TenantStageDashboardDto dataToDashboard(TenantStageDataDto data);
}
