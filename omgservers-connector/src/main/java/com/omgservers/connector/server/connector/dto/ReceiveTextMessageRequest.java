package com.omgservers.connector.server.connector.dto;

import com.omgservers.connector.server.handler.component.ConnectorConnection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveTextMessageRequest {

    @Valid
    @NotNull
    ConnectorConnection connectorConnection;

    @NotNull
    String message;
}
