package com.omgservers.model.matchClient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotBlank
    String groupName;

    @NotNull
    MatchClientConfigModel config;

    @NotNull
    Boolean deleted;
}
