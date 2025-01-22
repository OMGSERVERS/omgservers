package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.alias.AliasDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDto;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantProject.dto.TenantProjectDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TenantProjectMapper {

    TenantProjectDto modelToDto(TenantProjectModel model);

    AliasDto modelToDto(AliasModel aliasModel);

    @Mapping(target = "project", source = "project")
    @Mapping(target = "project.aliases", source = "aliases")
    @Mapping(target = "permissions", source = "projectPermissions")
    @Mapping(target = "stages", expression = "java(mapStages(data.getProjectStages(), data.getProjectAliases()))")
    @Mapping(target = "versions", source = "projectVersions")
    TenantProjectDetailsDto dataToDetails(TenantProjectDataDto data);

    default List<TenantStageDto> mapStages(final List<TenantStageModel> projectStages,
                                           final List<AliasModel> projectAliases) {
        final var aliasesGroupedByEntityId = projectAliases.stream()
                .collect(Collectors.groupingBy(AliasModel::getEntityId));

        return projectStages.stream()
                .map(stage -> {
                    final var stageDto = new TenantStageDto();
                    stageDto.setId(stage.getId());
                    stageDto.setTenantId(stage.getTenantId());
                    stageDto.setProjectId(stage.getProjectId());
                    stageDto.setCreated(stage.getCreated());

                    final var matchingAliases = aliasesGroupedByEntityId
                            .getOrDefault(stage.getId(), Collections.emptyList());
                    stageDto.setAliases(matchingAliases.stream()
                            .map(this::modelToDto)
                            .collect(Collectors.toList()));

                    return stageDto;
                })
                .collect(Collectors.toList());
    }
}
