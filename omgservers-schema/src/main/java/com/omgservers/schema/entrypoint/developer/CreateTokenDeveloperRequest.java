package com.omgservers.schema.entrypoint.developer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenDeveloperRequest {

    @NotBlank
    @Size(max = 64)
    String user;

    @NotBlank
    @Size(max = 64)
    @ToString.Exclude
    String password;
}
