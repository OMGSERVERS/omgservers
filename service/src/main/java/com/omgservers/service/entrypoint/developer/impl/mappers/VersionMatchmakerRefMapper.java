package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionMatchmakerRefDto;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionMatchmakerRefMapper {

    VersionMatchmakerRefDto modelToDto(VersionMatchmakerRefModel model);
}
