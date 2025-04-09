package com.omgservers.service.server.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistryResourceTypeEnum {

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

    public static RegistryResourceTypeEnum fromString(final String type) {
        for (var value : RegistryResourceTypeEnum.values()) {
            if (value.getType().equalsIgnoreCase(type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with value " + type);
    }

    final String type;
}
