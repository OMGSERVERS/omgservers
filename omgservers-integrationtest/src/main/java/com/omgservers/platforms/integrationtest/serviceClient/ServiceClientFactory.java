package com.omgservers.platforms.integrationtest.serviceClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import com.omgservers.platforms.integrationtest.testClient.TestClient;
import com.omgservers.platforms.integrationtest.testClient.WebsocketClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.Random;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServiceClientFactory {

    final GetConfigOperation getConfigOperation;
    final ObjectMapper objectMapper;

    public ServiceClient create() throws IOException, DeploymentException {
        final var servers = getConfigOperation.getServers();
        final var randomServer = servers.get(new Random().nextInt(servers.size()));
        final var uri = randomServer.externalAddress()
                .resolve("/omgservers/gateway");
        return new ServiceClient(objectMapper, uri);
    }
}
