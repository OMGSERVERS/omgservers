package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.StagePermissionDto;
import com.omgservers.schema.model.stagePermission.StagePermissionModel;
import org.mapstruct.Mapper;

@Mapper
public interface StagePermissionMapper {

    StagePermissionDto modelToDto(StagePermissionModel model);
}
