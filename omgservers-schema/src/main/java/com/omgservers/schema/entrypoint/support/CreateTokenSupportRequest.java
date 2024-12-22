package com.omgservers.schema.entrypoint.support;

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
public class CreateTokenSupportRequest {

    /**
     * User id or user alias.
     */
    @NotNull
    String user;

    @NotBlank
    @Size(max = 64)
    @ToString.Exclude
    String password;
}
