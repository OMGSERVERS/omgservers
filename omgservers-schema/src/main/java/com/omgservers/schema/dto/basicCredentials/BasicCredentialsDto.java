package com.omgservers.schema.dto.basicCredentials;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicCredentialsDto {

    @NotBlank
    Long userId;

    @NotBlank
    String password;
}
