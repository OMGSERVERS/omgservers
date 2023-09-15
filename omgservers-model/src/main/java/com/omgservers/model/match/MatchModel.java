package com.omgservers.model.match;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {

    @NotNull
    Long id;

    @NotNull
    Long matchmakerId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long runtimeId;

    @NotNull
    @EqualsAndHashCode.Exclude
    MatchConfigModel config;
}
