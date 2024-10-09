package com.omgservers.schema.model.player;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerAttributeDto {

    static public PlayerAttributeDto create(String name, String value) {
        final var attribute = new PlayerAttributeDto();
        attribute.type = PlayerAttributeTypeEnum.STRING;
        attribute.name = name;
        attribute.value = value;
        return attribute;
    }

    static public PlayerAttributeDto create(String name, Long value) {
        final var attribute = new PlayerAttributeDto();
        attribute.type = PlayerAttributeTypeEnum.LONG;
        attribute.name = name;
        attribute.value = value.toString();
        return attribute;
    }

    static public PlayerAttributeDto create(String name, Double value) {
        final var attribute = new PlayerAttributeDto();
        attribute.type = PlayerAttributeTypeEnum.DOUBLE;
        attribute.name = name;
        attribute.value = value.toString();
        return attribute;
    }

    static public PlayerAttributeDto create(String name, Boolean value) {
        final var attribute = new PlayerAttributeDto();
        attribute.type = PlayerAttributeTypeEnum.BOOLEAN;
        attribute.name = name;
        attribute.value = value.toString();
        return attribute;
    }

    PlayerAttributeTypeEnum type;
    String name;
    String value;
}
