package com.omgservers.schema.entrypoint.runtime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenRuntimeRequest {

    @NotNull
    Long runtimeId;

    @NotBlank
    @Size(max = 64)
    @ToString.Exclude
    String password;
}
