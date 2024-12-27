package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.alias.AliasDto;
import com.omgservers.schema.model.alias.AliasModel;
import org.mapstruct.Mapper;

@Mapper
public interface AliasMapper {

    AliasDto modelToDto(AliasModel model);
}
