package com.omgservers.schema.service.registry;

import lombok.Getter;

@Getter
public enum DockerRegistryActionEnum {

    /**
     * For accessing the registry.
     */
    PULL("pull"),

    /**
     * For adding to th registry.
     */
    PUSH("push");

    public static DockerRegistryActionEnum fromString(final String action) {
        for (var value : DockerRegistryActionEnum.values()) {
            if (value.getAction().equalsIgnoreCase(action)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with value " + action);
    }

    final String action;

    DockerRegistryActionEnum(final String action) {
        this.action = action;
    }
}
