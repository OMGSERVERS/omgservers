package com.omgservers.model.player;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerObjectModel {

    static public PlayerObjectModel create() {
        final var object = new PlayerObjectModel();
        object.body = new Object();
        return object;
    }

    @NotNull
    Object body;
}
