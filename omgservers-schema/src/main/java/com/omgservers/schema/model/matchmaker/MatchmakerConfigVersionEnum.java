package com.omgservers.schema.model.matchmaker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchmakerConfigVersionEnum {
    V1(1);

    final int version;
}
