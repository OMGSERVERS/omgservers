package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.ProjectPermissionDto;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectPermissionMapper {

    ProjectPermissionDto modelToDto(TenantProjectPermissionModel model);
}
