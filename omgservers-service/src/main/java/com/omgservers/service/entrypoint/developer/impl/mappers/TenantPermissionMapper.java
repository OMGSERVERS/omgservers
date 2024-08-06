package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.TenantPermissionDto;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import org.mapstruct.Mapper;

@Mapper
public interface TenantPermissionMapper {

    TenantPermissionDto modelToDto(TenantPermissionModel model);
}
