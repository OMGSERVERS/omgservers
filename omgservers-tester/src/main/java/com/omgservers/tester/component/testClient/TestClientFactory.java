package com.omgservers.tester.component.testClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.DeploymentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TestClientFactory {

    final GetConfigOperation getConfigOperation;
    final ObjectMapper objectMapper;

    public TestClient create() throws IOException, DeploymentException {
        final var uri = getConfigOperation.getConfig().gatewayUri().resolve("/omgservers/ws-gateway/v1/connection");
        return new TestClient(objectMapper, uri);
    }
}
