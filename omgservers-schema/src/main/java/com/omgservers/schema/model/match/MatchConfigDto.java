package com.omgservers.schema.model.match;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchConfigDto {

    static public MatchConfigDto create(final String mode) {
        final var matchConfig = new MatchConfigDto();
        matchConfig.setVersion(MatchConfigVersionEnum.V1);
        matchConfig.setMode(mode);
        return matchConfig;
    }

    @NotNull
    MatchConfigVersionEnum version;

    @NotBlank
    String mode;
}
