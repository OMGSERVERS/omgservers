package com.omgservers.schema.model.matchmakerRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchmakerRequestConfigDto {

    static public MatchmakerRequestConfigDto create() {
        final var config = new MatchmakerRequestConfigDto();
        config.setVersion(MatchmakerRequestConfigVersionEnum.V1);
        return config;
    }

    @NotNull
    MatchmakerRequestConfigVersionEnum version;
}
