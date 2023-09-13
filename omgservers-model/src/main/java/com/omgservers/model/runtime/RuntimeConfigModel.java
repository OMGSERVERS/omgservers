package com.omgservers.model.runtime;

import com.omgservers.model.version.VersionModeModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RuntimeConfigModel {

    static public RuntimeConfigModel create(VersionModeModel modeConfig) {
        final var runtimeConfig = new RuntimeConfigModel();
        runtimeConfig.setModeConfig(modeConfig);
        return runtimeConfig;
    }

    VersionModeModel modeConfig;
}
