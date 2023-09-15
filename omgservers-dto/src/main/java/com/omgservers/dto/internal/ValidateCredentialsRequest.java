package com.omgservers.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsRequest {

    @NotBlank
    @Size(max = 64)
    String username;

    @NotBlank
    @Size(max = 64)
    @ToString.Exclude
    String password;
}
