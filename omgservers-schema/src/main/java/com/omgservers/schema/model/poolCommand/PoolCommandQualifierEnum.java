package com.omgservers.schema.model.poolCommand;

import com.omgservers.schema.model.poolCommand.body.DeleteContainerPoolCommandBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolCommandQualifierEnum {
    DELETE_CONTAINER(DeleteContainerPoolCommandBodyDto.class);

    final Class<? extends PoolCommandBodyDto> bodyClass;
}
