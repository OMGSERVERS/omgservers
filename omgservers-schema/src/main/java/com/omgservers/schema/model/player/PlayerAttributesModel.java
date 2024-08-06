package com.omgservers.schema.model.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAttributesModel {

    static public PlayerAttributesModel create() {
        final var attributes = new PlayerAttributesModel();
        attributes.attributes = new ArrayList<>();
        return attributes;
    }

    List<PlayerAttributeModel> attributes;
}
