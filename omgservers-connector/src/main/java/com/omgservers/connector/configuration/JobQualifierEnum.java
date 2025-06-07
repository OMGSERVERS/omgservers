package com.omgservers.connector.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobQualifierEnum {
    IDLE_CONNECTIONS_HANDLER("idle-connections-handler", "1s"),
    MESSAGE_INTERCHANGER("message-interchanger", "1s"),
    TOKEN_REFRESHER("token-refresher", "60s");

    final String qualifier;
    final String interval;
}
