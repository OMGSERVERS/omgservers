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

    public static void validate(LogModel log) {
        if (log == null) {
            throw new IllegalArgumentException("log is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotBlank
    String message;
}
