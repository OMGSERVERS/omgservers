package com.omgservers.schema.model.matchmaker;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerConfigDto {

    static public MatchmakerConfigDto create() {
        final var matchmakerConfig = new MatchmakerConfigDto();
        matchmakerConfig.setVersion(MatchmakerConfigVersionEnum.V1);
        return matchmakerConfig;
    }

    @NotNull
    MatchmakerConfigVersionEnum version;
}
