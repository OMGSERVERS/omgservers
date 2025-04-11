package com.omgservers.schema.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserConfigVersionEnum {
    V1(1);

    final int version;
}
