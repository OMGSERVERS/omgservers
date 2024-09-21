package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionLobbyRefDto;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionLobbyRefMapper {

    VersionLobbyRefDto modelToDto(TenantLobbyRefModel model);
}
