package com.omgservers.model.matchClient;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchClientModel {

    @NotNull
    Long id;

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long userId;

    @NotNull
    Long clientId;
}
