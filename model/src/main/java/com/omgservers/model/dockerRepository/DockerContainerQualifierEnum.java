package com.omgservers.model.dockerRepository;

import lombok.Getter;

@Getter
public enum DockerContainerQualifierEnum {

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

    public static DockerContainerQualifierEnum fromString(String qualifier) {
        for (var value : DockerContainerQualifierEnum.values()) {
            if (value.getQualifier().equalsIgnoreCase(qualifier)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with qualifier " + qualifier);
    }

    final String qualifier;

    DockerContainerQualifierEnum(final String qualifier) {
        this.qualifier = qualifier;
    }
}
