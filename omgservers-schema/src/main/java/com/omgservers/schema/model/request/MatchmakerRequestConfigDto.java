package com.omgservers.schema.model.request;

import com.omgservers.schema.model.player.PlayerAttributesDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerRequestConfigDto {

    static public MatchmakerRequestConfigDto create(final PlayerAttributesDto attributes) {
        final var config = new MatchmakerRequestConfigDto();
        config.setAttributes(attributes);
        return config;
    }

    @NotNull
    PlayerAttributesDto attributes;
}
