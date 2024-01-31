package com.omgservers.service.module.client.impl.service.shortcutService;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.clientRuntime.ClientRuntimeModel;
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
import com.omgservers.service.module.client.ClientModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ShortcutServiceImpl implements ShortcutService {

    final ClientModule clientModule;

    @Override
    public Uni<ClientModel> getClient(final Long id) {
        final var request = new GetClientRequest(id);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    @Override
    public Uni<Boolean> syncClient(final ClientModel client) {
        final var request = new SyncClientRequest(client);
        return clientModule.getClientService().syncClient(request)
                .map(SyncClientResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientModule.getClientService().deleteClient(request)
                .map(DeleteClientResponse::getDeleted);
    }

    @Override
    public Uni<List<ClientRuntimeModel>> viewClientRuntimes(final Long clientId) {
        final var request = new ViewClientRuntimesRequest(clientId);
        return clientModule.getClientService().viewClientRuntimes(request)
                .map(ViewClientRuntimesResponse::getClientRuntimes);
    }

    @Override
    public Uni<List<ClientMessageModel>> viewClientMessages(final Long clientId) {
        final var request = new ViewClientMessagesRequest(clientId);
        return clientModule.getClientService().viewClientMessages(request)
                .map(ViewClientMessagesResponse::getClientMessages);
    }

    @Override
    public Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteClientMessages(final Long clientId,
                                             final List<Long> ids) {
        final var request = new DeleteClientMessagesRequest(clientId, ids);
        return clientModule.getClientService().deleteClientMessages(request)
                .map(DeleteClientMessagesResponse::getDeleted);
    }

    @Override
    public Uni<ClientRuntimeModel> getClientRuntime(Long clientId, Long id) {
        final var request = new GetClientRuntimeRequest(clientId, id);
        return clientModule.getClientService().getClientRuntime(request)
                .map(GetClientRuntimeResponse::getClientRuntime);
    }

    @Override
    public Uni<ClientRuntimeModel> selectLatestClientRuntime(Long clientId) {
        final var request = new SelectClientRuntimeRequest(clientId, SelectClientRuntimeRequest.Strategy.LATEST);
        return clientModule.getClientService().selectClientRuntime(request)
                .map(SelectClientRuntimeResponse::getClientRuntime);
    }

    @Override
    public Uni<Boolean> syncClientRuntime(final ClientRuntimeModel clientRuntime) {
        final var request = new SyncClientRuntimeRequest(clientRuntime);
        return clientModule.getClientService().syncClientRuntime(request)
                .map(SyncClientRuntimeResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteClientRuntime(final Long clientId, final Long id) {
        final var request = new DeleteClientRuntimeRequest(clientId, id);
        return clientModule.getClientService().deleteClientRuntime(request)
                .map(DeleteClientRuntimeResponse::getDeleted);
    }

    @Override
    public Uni<ClientRuntimeModel> findClientRuntime(final Long clientId, final Long runtimeId) {
        final var request = new FindClientRuntimeRequest(clientId, runtimeId);
        return clientModule.getClientService().findClientRuntime(request)
                .map(FindClientRuntimeResponse::getClientRuntime);
    }

    @Override
    public Uni<Boolean> findAndDeleteClientRuntime(final Long clientId, final Long runtimeId) {
        return findClientRuntime(clientId, runtimeId)
                .flatMap(clientRuntime -> deleteClientRuntime(clientId, clientRuntime.getId()));
    }
}
