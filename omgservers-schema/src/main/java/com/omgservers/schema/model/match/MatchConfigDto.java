package com.omgservers.schema.model.match;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchConfigDto {

    static public MatchConfigDto create() {
        final var matchConfig = new MatchConfigDto();
        matchConfig.setVersion(MatchConfigVersionEnum.V1);
        return matchConfig;
    }

    @NotNull
    MatchConfigVersionEnum version;
}
