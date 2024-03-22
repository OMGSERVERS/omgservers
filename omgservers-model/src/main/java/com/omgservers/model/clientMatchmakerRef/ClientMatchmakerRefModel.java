package com.omgservers.model.clientMatchmakerRef;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientMatchmakerRefModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Long clientId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long matchmakerId;

    @NotNull
    Boolean deleted;
}
