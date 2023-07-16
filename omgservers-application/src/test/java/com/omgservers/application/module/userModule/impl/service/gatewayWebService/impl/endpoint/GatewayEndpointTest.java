package com.omgservers.application.module.userModule.impl.service.gatewayWebService.impl.endpoint;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.websocket.*;
import java.net.URI;

@Slf4j
@QuarkusTest
class GatewayEndpointTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI gatewayUri;

    @Test
    public void testGateway() throws Exception {
        try (Session session = ContainerProvider.getWebSocketContainer()
                .connectToServer(GatewayClient.class, gatewayUri)) {
            session.getBasicRemote().sendText("""
                    {
                        "id": "123567890",
                        "qualifier": "SIGN_UP_MESSAGE",
                        "body": {
                            "tenant": "1D63857A-6E51-42A1-826D-760E098D1666",
                            "stage": "92C9122D-040E-4A32-9AA4-65C63C9DAB79",
                            "secret": "31736101274"
                        }
                    }
                    """);
        }
    }

    @ClientEndpoint
    public static class GatewayClient {
    }
}