package com.omgservers.service.server.state.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetServiceTokenRequest {

    @NotBlank
    String serviceToken;
}
