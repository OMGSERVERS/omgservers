package com.omgservers.schema.model.runtime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuntimeConfigVersionEnum {
    V1(1);

    final int version;
}
