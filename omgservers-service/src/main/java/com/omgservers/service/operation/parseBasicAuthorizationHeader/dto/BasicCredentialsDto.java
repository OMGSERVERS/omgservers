package com.omgservers.service.operation.parseBasicAuthorizationHeader.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicCredentialsDto {

    @NotBlank
    String user;

    @NotBlank
    String password;
}
