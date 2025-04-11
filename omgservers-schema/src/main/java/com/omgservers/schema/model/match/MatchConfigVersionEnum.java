package com.omgservers.schema.model.match;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchConfigVersionEnum {
    V1(1);

    final int version;
}
