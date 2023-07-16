package com.omgservers.application.module.tenantModule.model.stage;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StageConfigHandlerModel {

    static public StageConfigHandlerModel create() {
        final var config = new StageConfigHandlerModel();
        config.setUuid(null);
        config.setLatest(true);
        return config;
    }

    UUID uuid;
    Boolean latest;
}
