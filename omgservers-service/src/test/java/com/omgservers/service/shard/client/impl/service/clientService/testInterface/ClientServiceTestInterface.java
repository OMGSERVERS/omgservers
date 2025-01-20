package com.omgservers.service.shard.client.impl.service.clientService.testInterface;

import com.omgservers.schema.module.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.DeleteClientMessagesRequest;
import com.omgservers.schema.module.client.DeleteClientMessagesResponse;
import com.omgservers.schema.module.client.DeleteClientRequest;
import com.omgservers.schema.module.client.DeleteClientResponse;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefResponse;
import com.omgservers.schema.module.client.FindClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.FindClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.FindClientRuntimeRefRequest;
import com.omgservers.schema.module.client.FindClientRuntimeRefResponse;
import com.omgservers.schema.module.client.GetClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.GetClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.GetClientRuntimeRefRequest;
import com.omgservers.schema.module.client.GetClientRuntimeRefResponse;
import com.omgservers.schema.module.client.InterchangeRequest;
import com.omgservers.schema.module.client.InterchangeResponse;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.module.client.SyncClientRequest;
import com.omgservers.schema.module.client.SyncClientResponse;
import com.omgservers.schema.module.client.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.SyncClientRuntimeRefResponse;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.schema.module.client.ViewClientMessagesRequest;
import com.omgservers.schema.module.client.ViewClientMessagesResponse;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import com.omgservers.service.shard.client.impl.service.clientService.ClientService;
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

    public DeleteClientResponse deleteClient(final DeleteClientRequest request) {
        return clientService.deleteClient(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public InterchangeResponse interchange(final InterchangeRequest request) {
        return clientService.interchange(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewClientMessagesResponse viewClientMessages(final ViewClientMessagesRequest request) {
        return clientService.viewClientMessages(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientMessageResponse syncClientMessage(final SyncClientMessageRequest request) {
        return clientService.syncClientMessage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientMessageResponse syncClientMessageWithIdempotency(final SyncClientMessageRequest request) {
        return clientService.syncClientMessageWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteClientMessagesResponse deleteClientMessages(final DeleteClientMessagesRequest request) {
        return clientService.deleteClientMessages(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetClientRuntimeRefResponse getClientRuntimeRef(final GetClientRuntimeRefRequest request) {
        return clientService.getClientRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindClientRuntimeRefResponse findClientRuntimeRef(final FindClientRuntimeRefRequest request) {
        return clientService.findClientRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewClientRuntimeRefsResponse viewClientRuntimeRefs(final ViewClientRuntimeRefsRequest request) {
        return clientService.viewClientRuntimeRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientRuntimeRefResponse syncClientRuntimeRef(final SyncClientRuntimeRefRequest request) {
        return clientService.syncClientRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteClientRuntimeRefResponse deleteClientRuntimeRef(final DeleteClientRuntimeRefRequest request) {
        return clientService.deleteClientRuntimeRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetClientMatchmakerRefResponse getClientMatchmakerRef(final GetClientMatchmakerRefRequest request) {
        return clientService.getClientMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindClientMatchmakerRefResponse findClientMatchmakerRef(final FindClientMatchmakerRefRequest request) {
        return clientService.findClientMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewClientMatchmakerRefsResponse viewClientMatchmakerRefs(final ViewClientMatchmakerRefsRequest request) {
        return clientService.viewClientMatchmakerRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientMatchmakerRefResponse syncClientMatchmakerRef(final SyncClientMatchmakerRefRequest request) {
        return clientService.syncClientMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteClientMatchmakerRefResponse deleteClientMatchmakerRef(final DeleteClientMatchmakerRefRequest request) {
        return clientService.deleteClientMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
