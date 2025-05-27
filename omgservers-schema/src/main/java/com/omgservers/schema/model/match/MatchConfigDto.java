package com.omgservers.schema.model.match;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @JsonSetter(nulls = Nulls.SKIP)
    MatchConfigVersionEnum version = MatchConfigVersionEnum.V1;

    @NotBlank
    @Size(max = 64)
    String mode;
}
