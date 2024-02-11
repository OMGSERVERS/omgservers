package com.omgservers.model.handler;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlerModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Boolean deleted;
}
