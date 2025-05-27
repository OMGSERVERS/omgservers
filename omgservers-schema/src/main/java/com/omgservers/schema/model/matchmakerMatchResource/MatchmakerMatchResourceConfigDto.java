package com.omgservers.schema.model.matchmakerMatchResource;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.omgservers.schema.model.match.MatchConfigDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerMatchResourceConfigDto {

    static public MatchmakerMatchResourceConfigDto create(final MatchConfigDto matchConfig) {
        final var matchmakerMatchResourceConfigDto = new MatchmakerMatchResourceConfigDto();
        matchmakerMatchResourceConfigDto.setVersion(MatchmakerMatchResourceConfigVersionEnum.V1);
        matchmakerMatchResourceConfigDto.setMatchConfig(matchConfig);
        return matchmakerMatchResourceConfigDto;
    }

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    MatchmakerMatchResourceConfigVersionEnum version = MatchmakerMatchResourceConfigVersionEnum.V1;

    @Valid
    @NotNull
    MatchConfigDto matchConfig;
}
