package com.omgservers.schema.model.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAttributesDto {

    static public PlayerAttributesDto create() {
        final var attributes = new PlayerAttributesDto();
        attributes.attributes = new ArrayList<>();
        return attributes;
    }

    List<PlayerAttributeDto> attributes;
}
