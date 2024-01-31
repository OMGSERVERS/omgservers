package com.omgservers.service.module.client.impl.service.shortcutService;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ShortcutService {
    Uni<ClientModel> getClient(Long id);

    Uni<Boolean> syncClient(ClientModel client);

    Uni<Boolean> deleteClient(Long clientId);

    Uni<List<ClientRuntimeModel>> viewClientRuntimes(Long clientId);

    Uni<List<ClientMessageModel>> viewClientMessages(Long clientId);

    Uni<Boolean> syncClientMessage(ClientMessageModel clientMessage);

    Uni<Boolean> deleteClientMessages(Long clientId, List<Long> ids);

    Uni<ClientRuntimeModel> getClientRuntime(Long clientId, Long id);

    Uni<ClientRuntimeModel> selectLatestClientRuntime(Long clientId);

    Uni<Boolean> syncClientRuntime(ClientRuntimeModel clientRuntime);

    Uni<Boolean> deleteClientRuntime(Long clientId, Long id);

    Uni<ClientRuntimeModel> findClientRuntime(Long clientId, Long runtimeId);

    Uni<Boolean> findAndDeleteClientRuntime(Long clientId, Long runtimeId);

}
