package com.omgservers.service.module.client.impl.service.webService.impl;

import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRequest;
import com.omgservers.model.dto.client.FindClientRuntimeResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRequest;
import com.omgservers.model.dto.client.GetClientRuntimeResponse;
import com.omgservers.model.dto.client.HandleMessageRequest;
import com.omgservers.model.dto.client.HandleMessageResponse;
import com.omgservers.model.dto.client.ReceiveMessagesRequest;
import com.omgservers.model.dto.client.ReceiveMessagesResponse;
import com.omgservers.model.dto.client.SelectClientRuntimeRequest;
import com.omgservers.model.dto.client.SelectClientRuntimeResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimesRequest;
import com.omgservers.model.dto.client.ViewClientRuntimesResponse;
import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import com.omgservers.service.module.client.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final ClientService clientService;

    @Override
    public Uni<GetClientResponse> getClient(final GetClientRequest request) {
        return clientService.getClient(request);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(final SyncClientRequest request) {
        return clientService.syncClient(request);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(final DeleteClientRequest request) {
        return clientService.deleteClient(request);
    }

    @Override
    public Uni<HandleMessageResponse> handleMessage(final HandleMessageRequest request) {
        return clientService.handleMessage(request);
    }

    @Override
    public Uni<ReceiveMessagesResponse> receiveMessages(final ReceiveMessagesRequest request) {
        return clientService.receiveMessages(request);
    }

    @Override
    public Uni<ViewClientMessagesResponse> viewClientMessages(final ViewClientMessagesRequest request) {
        return clientService.viewClientMessages(request);
    }

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessage(final SyncClientMessageRequest request) {
        return clientService.syncClientMessage(request);
    }

    @Override
    public Uni<GetClientRuntimeResponse> getClientRuntime(final GetClientRuntimeRequest request) {
        return clientService.getClientRuntime(request);
    }

    @Override
    public Uni<DeleteClientMessagesResponse> deleteClientMessages(
            final DeleteClientMessagesRequest request) {
        return clientService.deleteClientMessages(request);
    }

    @Override
    public Uni<FindClientRuntimeResponse> findClientRuntime(final FindClientRuntimeRequest request) {
        return clientService.findClientRuntime(request);
    }

    @Override
    public Uni<ViewClientRuntimesResponse> viewClientRuntimes(final ViewClientRuntimesRequest request) {
        return clientService.viewClientRuntimes(request);
    }

    @Override
    public Uni<SelectClientRuntimeResponse> selectClientRuntime(final SelectClientRuntimeRequest request) {
        return clientService.selectClientRuntime(request);
    }

    @Override
    public Uni<SyncClientRuntimeResponse> syncClientRuntime(final SyncClientRuntimeRequest request) {
        return clientService.syncClientRuntime(request);
    }

    @Override
    public Uni<DeleteClientRuntimeResponse> deleteClientRuntime(final DeleteClientRuntimeRequest request) {
        return clientService.deleteClientRuntime(request);
    }
}
