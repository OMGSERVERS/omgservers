package com.omgservers.module.gateway.impl.service.websocketService.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveTextMessageRequest {

    @NotNull
    Session session;

    @NotBlank
    String message;
}