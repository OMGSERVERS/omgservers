package com.omgservers.schema.model.player;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerConfigDto {

    static public PlayerConfigDto create() {
        final var config = new PlayerConfigDto();
        return config;
    }
}
