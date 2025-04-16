package com.omgservers.service.server.state.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetServiceTokenRequest {

    @NotBlank
    String serviceToken;
}
