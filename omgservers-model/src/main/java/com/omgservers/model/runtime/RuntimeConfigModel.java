package com.omgservers.model.runtime;

import com.omgservers.model.version.VersionModeModel;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    VersionModeModel modeConfig;
}
