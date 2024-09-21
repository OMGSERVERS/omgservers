package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionMapper {

    VersionDto modelToDto(TenantVersionModel model);
}
