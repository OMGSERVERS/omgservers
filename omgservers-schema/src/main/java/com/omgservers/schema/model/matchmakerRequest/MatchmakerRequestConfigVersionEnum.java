package com.omgservers.schema.model.matchmakerRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchmakerRequestConfigVersionEnum {
    V1(1);

    final int version;
}
