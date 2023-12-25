package com.omgservers.model.dto.gateway;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloseConnectionRequest {

    @NotNull
    URI server;

    @NotNull
    Long connectionId;

    @NotNull
    String reason;
}
