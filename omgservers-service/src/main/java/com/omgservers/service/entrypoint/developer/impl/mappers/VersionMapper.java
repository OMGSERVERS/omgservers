package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionDto;
import com.omgservers.schema.model.version.VersionModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionMapper {

    VersionDto modelToDto(VersionModel model);
}
