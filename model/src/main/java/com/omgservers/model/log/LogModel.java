package com.omgservers.model.log;

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
public class LogModel {

    @NotNull
    Long id;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotBlank
    String message;
}
