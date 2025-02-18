package com.omgservers.schema.model.poolSeverContainer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolContainerLabel {
    TENANT("com.omgservers.tenant"),
    PROJECT("com.omgservers.project"),
    STAGE("com.omgservers.stage"),
    VERSION("com.omgservers.version");

    final String qualifier;
}
