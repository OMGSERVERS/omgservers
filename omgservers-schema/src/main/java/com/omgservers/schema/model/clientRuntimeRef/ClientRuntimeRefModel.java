package com.omgservers.schema.model.clientRuntimeRef;

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
public class ClientRuntimeRefModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Long runtimeId;

    @NotNull
    Boolean deleted;
}
