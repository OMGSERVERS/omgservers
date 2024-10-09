package com.omgservers.schema.model.runtimeCommand;

import com.omgservers.schema.model.runtimeCommand.body.AddClientRuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.body.AddMatchClientRuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.body.InitRuntimeCommandBodyDto;
import lombok.Getter;

@Getter
public enum RuntimeCommandQualifierEnum {
    INIT_RUNTIME(InitRuntimeCommandBodyDto.class),
    ADD_CLIENT(AddClientRuntimeCommandBodyDto.class),
    ADD_MATCH_CLIENT(AddMatchClientRuntimeCommandBodyDto.class),
    DELETE_CLIENT(DeleteClientRuntimeCommandBodyDto.class),
    HANDLE_MESSAGE(HandleMessageRuntimeCommandBodyDto.class);

    final Class<? extends RuntimeCommandBodyDto> bodyClass;

    RuntimeCommandQualifierEnum(Class<? extends RuntimeCommandBodyDto> bodyClass) {
        this.bodyClass = bodyClass;
    }
}
