package com.omgservers.ctl.dto.region;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RegionEnum {
    FRA1("fra1",
            URI.create("https://api.fra1.omgservers.com"),
            URI.create("https://registry.fra1.omgservers.com"));

    final String region;
    final URI api;
    final URI registry;

    @JsonValue
    public String toJson() {
        return region;
    }

    @JsonCreator
    public static RegionEnum fromString(final String region) {
        return Arrays.stream(RegionEnum.values())
                .filter(value -> value.region.equals(region))
                .findFirst()
                .orElseThrow();
    }
}
