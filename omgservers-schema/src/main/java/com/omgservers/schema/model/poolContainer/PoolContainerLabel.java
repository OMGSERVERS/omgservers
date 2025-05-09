package com.omgservers.schema.model.poolContainer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolContainerLabel {
    TENANT("com.omgservers.tenant"),
    PROJECT("com.omgservers.project"),
    STAGE("com.omgservers.stage"),
    VERSION("com.omgservers.version"),
    QUALIFIER("com.omgservers.qualifier");

    final String qualifier;
}
