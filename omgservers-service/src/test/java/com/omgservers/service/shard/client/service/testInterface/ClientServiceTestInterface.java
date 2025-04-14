package com.omgservers.service.shard.client.service.testInterface;

import com.omgservers.schema.shard.client.client.DeleteClientRequest;
import com.omgservers.schema.shard.client.client.DeleteClientResponse;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.schema.shard.client.client.SyncClientRequest;
import com.omgservers.schema.shard.client.client.SyncClientResponse;
import com.omgservers.schema.shard.client.clientMessage.DeleteClientMessagesRequest;
import com.omgservers.schema.shard.client.clientMessage.DeleteClientMessagesResponse;
import com.omgservers.schema.shard.client.clientMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.client.clientMessage.InterchangeMessagesResponse;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageResponse;
import com.omgservers.schema.shard.client.clientMessage.ViewClientMessagesRequest;
import com.omgservers.schema.shard.client.clientMessage.ViewClientMessagesResponse;
import com.omgservers.schema.shard.client.clientRuntimeRef.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.DeleteClientRuntimeRefResponse;
import com.omgservers.schema.shard.client.clientRuntimeRef.FindClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.FindClientRuntimeRefResponse;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefResponse;
import com.omgservers.schema.shard.client.clientRuntimeRef.SyncClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.SyncClientRuntimeRefResponse;
import com.omgservers.schema.shard.client.clientRuntimeRef.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.ViewClientRuntimeRefsResponse;
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
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientResponse syncClient(final SyncClientRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteClientResponse deleteClient(final DeleteClientRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public InterchangeMessagesResponse interchange(final InterchangeMessagesRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewClientMessagesResponse viewClientMessages(final ViewClientMessagesRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientMessageResponse syncClientMessage(final SyncClientMessageRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientMessageResponse syncClientMessageWithIdempotency(final SyncClientMessageRequest request) {
        return clientService.executeWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteClientMessagesResponse deleteClientMessages(final DeleteClientMessagesRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetClientRuntimeRefResponse getClientRuntimeRef(final GetClientRuntimeRefRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindClientRuntimeRefResponse findClientRuntimeRef(final FindClientRuntimeRefRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewClientRuntimeRefsResponse viewClientRuntimeRefs(final ViewClientRuntimeRefsRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientRuntimeRefResponse syncClientRuntimeRef(final SyncClientRuntimeRefRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteClientRuntimeRefResponse deleteClientRuntimeRef(final DeleteClientRuntimeRefRequest request) {
        return clientService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
