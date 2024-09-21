package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.ProjectDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {

    ProjectDto modelToDto(TenantProjectModel model);
}
