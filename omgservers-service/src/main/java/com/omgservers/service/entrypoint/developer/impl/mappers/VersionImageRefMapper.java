package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.VersionImageRefDto;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import org.mapstruct.Mapper;

@Mapper
public interface VersionImageRefMapper {

    VersionImageRefDto modelToDto(TenantImageRefModel model);
}
