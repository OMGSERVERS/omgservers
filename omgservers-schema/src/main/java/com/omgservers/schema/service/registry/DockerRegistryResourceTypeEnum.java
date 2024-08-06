package com.omgservers.schema.service.registry;

import lombok.Getter;

@Getter
public enum DockerRegistryResourceTypeEnum {

    /**
     * Represents a single repository within a registry.
     */
    REPOSITORY("repository"),

    /**
     * Represents a single repository of plugins within a registry.
     */
    REPOSITORY_PLUGIN("repository(plugin)"),

    /**
     * Represents the entire registry.
     */
    REGISTRY("registry");

    public static DockerRegistryResourceTypeEnum fromString(final String type) {
        for (var value : DockerRegistryResourceTypeEnum.values()) {
            if (value.getType().equalsIgnoreCase(type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with value " + type);
    }

    final String type;

    DockerRegistryResourceTypeEnum(final String type) {
        this.type = type;
    }
}
