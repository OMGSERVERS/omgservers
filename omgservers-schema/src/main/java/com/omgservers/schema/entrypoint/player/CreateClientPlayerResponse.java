package com.omgservers.schema.entrypoint.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientPlayerResponse {

    Long clientId;

    ConnectorConfig connectorConfig;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConnectorConfig {
        URI connectionUrl;

        @ToString.Exclude
        String secWebSocketProtocol;
    }
}
