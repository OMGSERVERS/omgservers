package com.omgservers.testDataFactory;

import com.omgservers.schema.model.client.ClientConfigDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.client.client.SyncClientRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.SyncClientRuntimeRefRequest;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.shard.client.service.testInterface.ClientServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientTestDataFactory {

    final ClientServiceTestInterface clientService;

    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;
    final ClientModelFactory clientModelFactory;

    public ClientModel createClient(final PlayerModel player,
                                    final DeploymentModel deployment) {
        final var userId = player.getUserId();
        final var playerId = player.getId();
        final var deploymentId = deployment.getId();

        final var client = clientModelFactory.create(userId, playerId, deploymentId, ClientConfigDto.create());
        final var syncClientRequest = new SyncClientRequest(client);
        clientService.syncClient(syncClientRequest);
        return client;
    }

    public ClientRuntimeRefModel createClientRuntimeRef(final ClientModel client,
                                                        final RuntimeModel runtime) {
        final var clientId = client.getId();
        final var runtimeId = runtime.getId();
        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(clientId, runtimeId);
        final var syncClientRuntimeRefRequest = new SyncClientRuntimeRefRequest(clientRuntimeRef);
        clientService.syncClientRuntimeRef(syncClientRuntimeRefRequest);
        return clientRuntimeRef;
    }
}
