package com.omgservers.application.module.userModule.model.player;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerConfigModel {

    static public PlayerConfigModel create() {
        final var config = new PlayerConfigModel();
        return config;
    }
}
