package com.omgservers.dispatcher.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobQualifierEnum {
    IDLE_CONNECTIONS_HANDLER("idle-connections-handler", "1s");

    final String qualifier;
    final String interval;
}
