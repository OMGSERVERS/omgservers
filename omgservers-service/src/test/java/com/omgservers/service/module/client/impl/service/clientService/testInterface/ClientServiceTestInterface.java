package com.omgservers.service.module.client.impl.service.clientService.testInterface;

import com.omgservers.model.dto.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.DeleteClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefResponse;
import com.omgservers.model.dto.client.FindClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.FindClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRefRequest;
import com.omgservers.model.dto.client.FindClientRuntimeRefResponse;
import com.omgservers.model.dto.client.GetClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.GetClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRefRequest;
import com.omgservers.model.dto.client.GetClientRuntimeRefResponse;
import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRefRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeRefResponse;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
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
