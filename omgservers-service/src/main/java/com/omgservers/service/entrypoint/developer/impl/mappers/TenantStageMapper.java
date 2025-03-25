package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TenantStageMapper {

    TenantStageDto modelToDto(TenantStageModel model);

    @Mapping(target = "stage", source = "stage")
    @Mapping(target = "stage.aliases", source = "aliases")
    @Mapping(target = "permissions", source = "permissions")
    @Mapping(target = "deployments", source = "deployments")
    TenantStageDetailsDto dataToDetails(TenantStageDataDto data);
}
