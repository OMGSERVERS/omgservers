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
public class CreateConnectorRequest {

    @Valid
    @NotNull
    ConnectorConnection connectorConnection;
}
