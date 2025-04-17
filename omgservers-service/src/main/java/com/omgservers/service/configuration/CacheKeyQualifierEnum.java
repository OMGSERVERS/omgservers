package com.omgservers.service.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheKeyQualifierEnum {
    INDEX_CONFIG("service:index:config", 0),
    RUNTIME_LAST_ACTIVITY("service:runtime:%d:last-activity", 60),
    CLIENT_LAST_ACTIVITY("service:client:%d:last-activity", 60);

    final String key;
    final long timeoutInSeconds;
}
