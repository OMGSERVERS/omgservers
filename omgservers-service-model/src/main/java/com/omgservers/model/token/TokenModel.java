package com.omgservers.model.token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {

    @NotNull
    Long id;

    @NotNull
    Long userId;

    @NotNull
    Instant created;

    @NotNull
    Instant expire;

    @NotBlank
    @Size(max = 1024)
    @ToString.Exclude
    String hash;
}
