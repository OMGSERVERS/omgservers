package com.omgservers.service.server.registry.dto;

import lombok.Getter;

@Getter
public enum DockerRegistryContainerQualifierEnum {

    /**
     * Image is only for lobby containers.
     */
    LOBBY("lobby"),

    /**
     * Image is only for match containers.
     */
    MATCH("match"),

    /**
     * Universal image is for lobby and match containers.
     */
    UNIVERSAL("universal");

    public static DockerRegistryContainerQualifierEnum fromString(final String qualifier) {
        for (var value : DockerRegistryContainerQualifierEnum.values()) {
            if (value.getQualifier().equalsIgnoreCase(qualifier)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with value " + qualifier);
    }

    final String qualifier;

    DockerRegistryContainerQualifierEnum(final String qualifier) {
        this.qualifier = qualifier;
    }
}
