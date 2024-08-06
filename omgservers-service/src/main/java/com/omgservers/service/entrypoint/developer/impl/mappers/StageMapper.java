package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.StageDto;
import com.omgservers.schema.model.stage.StageModel;
import org.mapstruct.Mapper;

@Mapper
public interface StageMapper {

    StageDto modelToDto(StageModel model);
}
