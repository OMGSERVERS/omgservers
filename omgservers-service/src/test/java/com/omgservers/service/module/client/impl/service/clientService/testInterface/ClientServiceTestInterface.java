package com.omgservers.service.module.client.impl.service.clientService.testInterface;

import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final ClientService clientService;

    public GetClientResponse getClient(final GetClientRequest request) {
        return clientService.getClient(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientResponse syncClient(final SyncClientRequest request) {
        return clientService.syncClient(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
