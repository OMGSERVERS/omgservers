package com.omgservers.model.stage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StageConfigModel {

    static public StageConfigModel create() {
        StageConfigModel config = new StageConfigModel();
        return config;
    }
}
