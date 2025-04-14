package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.alias.AliasDto;
import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDto;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.shard.tenant.tenant.dto.TenantDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TenantMapper {

    TenantDto modelToDto(TenantModel model);

    AliasDto modelToDto(AliasModel aliasModel);

    @Mapping(target = "tenant", source = "tenant")
    @Mapping(target = "tenant.aliases", source = "aliases")
    @Mapping(target = "permissions", source = "tenantPermissions")
    @Mapping(target = "projects", expression = "java(mapProjects(data.getTenantProjects(), data.getTenantAliases()))")
    TenantDetailsDto dataToDetails(TenantDataDto data);

    default List<TenantProjectDto> mapProjects(final List<TenantProjectModel> tenantProjects,
                                               final List<AliasModel> tenantAliases) {
        final var aliasesGroupedByEntityId = tenantAliases.stream()
                .collect(Collectors.groupingBy(AliasModel::getEntityId));

        return tenantProjects.stream()
                .map(project -> {
                    final var projectDto = new TenantProjectDto();
                    projectDto.setId(project.getId());
                    projectDto.setTenantId(project.getTenantId());
                    projectDto.setCreated(project.getCreated());

                    final var matchingAliases = aliasesGroupedByEntityId
                            .getOrDefault(project.getId(), Collections.emptyList());
                    projectDto.setAliases(matchingAliases.stream()
                            .map(this::modelToDto)
                            .collect(Collectors.toList()));

                    return projectDto;
                })
                .collect(Collectors.toList());
    }
}
