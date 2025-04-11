package com.omgservers.schema.model.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientConfigVersionEnum {
    V1(1);

    final int version;
}
