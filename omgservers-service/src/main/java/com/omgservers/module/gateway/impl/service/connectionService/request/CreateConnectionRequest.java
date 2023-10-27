package com.omgservers.module.gateway.impl.service.connectionService.request;

import jakarta.validation.constraints.NotNull;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectionRequest {

    @NotNull
    Session session;
}
