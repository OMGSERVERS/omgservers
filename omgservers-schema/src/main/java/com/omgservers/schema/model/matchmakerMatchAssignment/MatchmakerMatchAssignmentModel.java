package com.omgservers.schema.model.matchmakerMatchAssignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerMatchAssignmentModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotBlank
    String groupName;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    MatchmakerMatchAssignmentConfigDto config;

    @NotNull
    Boolean deleted;
}
