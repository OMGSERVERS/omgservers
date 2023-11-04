package com.omgservers.utils.testClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.DeploymentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TestClientFactory {

    final GenerateIdOperation generateIdOperation;
    final ObjectMapper objectMapper;

    public TestClient create(URI uri) throws IOException, DeploymentException {
        return new TestClient(generateIdOperation, objectMapper, uri);
    }
}
