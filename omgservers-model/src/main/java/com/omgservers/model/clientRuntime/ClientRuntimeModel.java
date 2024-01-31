package com.omgservers.model.clientRuntime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRuntimeModel {

    @NotNull
    Long id;

    @NotNull
    Long clientId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long runtimeId;

    @NotNull
    Boolean deleted;
}
