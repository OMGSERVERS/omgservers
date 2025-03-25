package com.omgservers.service.entrypoint.developer.impl.mappers;

import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.DeploymentDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.DeploymentDto;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.module.deployment.deployment.dto.DeploymentDataDto;
import org.mapstruct.Mapper;

@Mapper
public interface DeploymentMapper {

    DeploymentDto modelToDto(DeploymentModel model);

    DeploymentDetailsDto dataToDetails(DeploymentDataDto data);
}
