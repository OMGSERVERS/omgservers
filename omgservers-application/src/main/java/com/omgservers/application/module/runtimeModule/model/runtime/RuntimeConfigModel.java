package com.omgservers.application.module.runtimeModule.model.runtime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RuntimeConfigModel {

    static public RuntimeConfigModel create() {
        final var runtimeConfig = new RuntimeConfigModel();
        return runtimeConfig;
    }
}
