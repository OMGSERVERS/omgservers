package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.TenantDto;
import com.omgservers.schema.model.tenant.TenantModel;
import org.mapstruct.Mapper;

@Mapper
public interface TenantMapper {

    TenantDto modelToDto(TenantModel model);
}
