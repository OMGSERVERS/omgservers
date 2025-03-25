package com.omgservers.schema.model.runtimeCommand;

import com.omgservers.schema.model.runtimeCommand.body.AssignClientRuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.body.RemoveClientRuntimeCommandBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuntimeCommandQualifierEnum {
    ASSIGN_CLIENT(AssignClientRuntimeCommandBodyDto.class),
    REMOVE_CLIENT(RemoveClientRuntimeCommandBodyDto.class);

    final Class<? extends RuntimeCommandBodyDto> bodyClass;
}
