package com.omgservers.schema.model.request;

import com.omgservers.schema.model.player.PlayerAttributesModel;
import com.omgservers.schema.model.player.PlayerAttributesModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerRequestConfigModel {

    static public MatchmakerRequestConfigModel create(final PlayerAttributesModel attributes) {
        final var config = new MatchmakerRequestConfigModel();
        config.setAttributes(attributes);
        return config;
    }

    @NotNull
    PlayerAttributesModel attributes;
}
