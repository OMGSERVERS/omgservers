package com.omgservers.service.server.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistryActionEnum {

    /**
     * For accessing the registry.
     */
    PULL("pull"),

    /**
     * For adding to the registry.
     */
    PUSH("push");

    public static RegistryActionEnum fromString(final String action) {
        for (var value : RegistryActionEnum.values()) {
            if (value.getAction().equalsIgnoreCase(action)) {
                return value;
            }
        }
        throw new IllegalArgumentException("no enum constant with value " + action);
    }

    final String action;
}
