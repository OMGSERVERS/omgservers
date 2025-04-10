package com.omgservers.schema.model.poolRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PoolRequestConfigVersionEnum {
    V1(1);

    final int version;
}
