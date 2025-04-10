package com.omgservers.schema.entrypoint.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BcryptHashAdminRequest {

    @NotBlank
    String value;
}
