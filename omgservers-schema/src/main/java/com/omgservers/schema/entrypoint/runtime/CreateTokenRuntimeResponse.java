package com.omgservers.schema.entrypoint.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenRuntimeResponse {

    @ToString.Exclude
    String apiToken;

    DispatcherConfig dispatcherConfig;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DispatcherConfig {
        URI connectionUrl;

        @ToString.Exclude
        String secWebSocketProtocol;
    }
}
