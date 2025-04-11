package com.omgservers.schema.model.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LobbyConfigVersionEnum {
    V1(1);

    final int version;
}
