package com.omgservers.schema.model.pool;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolConfigVersionEnum {
    V1(1);

    final int version;
}
