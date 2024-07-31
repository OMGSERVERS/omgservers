package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionImageRefDto;
import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionImageRefMapper {

    VersionImageRefDto modelToDto(VersionImageRefModel model);
}
