package com.omgservers.schema.model.poolContainer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolContainerConfigVersionEnum {
    V1(1);

    final int version;
}
