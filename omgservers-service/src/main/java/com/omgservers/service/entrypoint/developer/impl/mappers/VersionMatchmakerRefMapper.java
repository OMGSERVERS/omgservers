package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionMatchmakerRefDto;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionMatchmakerRefMapper {

    VersionMatchmakerRefDto modelToDto(TenantMatchmakerRefModel model);
}
