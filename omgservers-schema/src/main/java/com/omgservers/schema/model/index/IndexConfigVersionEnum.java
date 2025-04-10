package com.omgservers.schema.model.index;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndexConfigVersionEnum {
    V1(1);

    final int version;
}
