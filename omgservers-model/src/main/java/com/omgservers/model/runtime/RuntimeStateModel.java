package com.omgservers.model.runtime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RuntimeStateModel {

    static public RuntimeStateModel create() {
        final var runtimeState = new RuntimeStateModel();
        // Initial state of runtime is empty json
        runtimeState.setState("{}");
        return runtimeState;
    }

    String state;
}
