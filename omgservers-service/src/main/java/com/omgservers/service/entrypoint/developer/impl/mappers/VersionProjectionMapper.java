package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionProjectionDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionProjectionMapper {

    VersionProjectionDto modelToDto(TenantVersionProjectionModel model);
}
