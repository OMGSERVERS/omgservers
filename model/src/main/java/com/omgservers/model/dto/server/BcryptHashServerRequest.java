package com.omgservers.model.dto.server;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BcryptHashServerRequest {

    @NotBlank
    String value;
}
