package com.omgservers.model.matchmaker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    Boolean deleted;
}
