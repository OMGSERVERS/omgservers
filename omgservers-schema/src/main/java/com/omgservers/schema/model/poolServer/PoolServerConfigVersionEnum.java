package com.omgservers.schema.model.poolServer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolServerConfigVersionEnum {
    V1(1);

    final int version;
}
