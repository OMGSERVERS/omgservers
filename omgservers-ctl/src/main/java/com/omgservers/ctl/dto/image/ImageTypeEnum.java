package com.omgservers.ctl.dto.image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ImageTypeEnum {

    LOBBY("lobby"),
    MATCH("match"),
    UNIVERSAL("universal");

    final String type;

    @JsonValue
    public String toJson() {
        return type;
    }

    @JsonCreator
    public static ImageTypeEnum fromString(final String type) {
        return Arrays.stream(ImageTypeEnum.values())
                .filter(value -> value.type.equals(type))
                .findFirst()
                .orElseThrow();
    }

    public TenantImageQualifierEnum toQualifier() {
        final var thisName = name();
        return Arrays.stream(TenantImageQualifierEnum.values())
                .filter(value -> value.name().equals(thisName))
                .findFirst()
                .orElseThrow();
    }
}
