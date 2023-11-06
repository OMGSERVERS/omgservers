package com.omgservers.model.matchmakerCommand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MatchmakerCommandDeserializer.class)
public class MatchmakerCommandModel {

    @NotNull
    Long id;

    @NotNull
    Long matchmakerId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    MatchmakerCommandQualifierEnum qualifier;

    @NotNull
    @EqualsAndHashCode.Exclude
    MatchmakerCommandBodyModel body;

    @NotNull
    Boolean deleted;
}
