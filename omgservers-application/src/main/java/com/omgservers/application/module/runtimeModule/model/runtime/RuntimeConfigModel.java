package com.omgservers.application.module.runtimeModule.model.runtime;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeConfigModel {

    static public RuntimeConfigModel create(RuntimeTypeEnum type) {
        final var runtimeConfig = new RuntimeConfigModel();
        runtimeConfig.setType(type);
        return runtimeConfig;
    }

    static public void validateRuntimeConfigModel(RuntimeConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    RuntimeTypeEnum type;
}
