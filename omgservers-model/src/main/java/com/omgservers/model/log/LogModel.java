package com.omgservers.model.log;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotBlank
    String message;
}
