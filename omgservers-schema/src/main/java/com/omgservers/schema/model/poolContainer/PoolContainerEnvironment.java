package com.omgservers.schema.model.poolContainer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolContainerEnvironment {
    RUNTIME_ID("OMGSERVERS_RUNTIME_ID"),
    PASSWORD("OMGSERVERS_PASSWORD"),
    QUALIFIER("OMGSERVERS_QUALIFIER"),
    SERVICE_URL("OMGSERVERS_SERVICE_URL");

    final String variable;
}
