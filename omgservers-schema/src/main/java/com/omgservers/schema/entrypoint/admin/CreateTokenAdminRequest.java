package com.omgservers.schema.entrypoint.admin;

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
public class CreateTokenAdminRequest {

    @NotNull
    Long userId;

    @NotBlank
    @Size(max = 64)
    @ToString.Exclude
    String password;
}
