package com.omgservers.schema.model.matchmakerRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchmakerRequestConfigDto {

    static public MatchmakerRequestConfigDto create() {
        final var config = new MatchmakerRequestConfigDto();
        return config;
    }
}
