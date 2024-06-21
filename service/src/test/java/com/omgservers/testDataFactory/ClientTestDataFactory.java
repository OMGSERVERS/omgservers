package com.omgservers.testDataFactory;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeRefRequest;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.factory.client.ClientMatchmakerRefModelFactory;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.module.client.impl.service.clientService.testInterface.ClientServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientTestDataFactory {

    final ClientServiceTestInterface clientService;

    final ClientMatchmakerRefModelFactory clientMatchmakerRefModelFactory;
    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;
    final ClientModelFactory clientModelFactory;

    public ClientModel createClient(final PlayerModel player,
                                    final TenantModel tenant,
                                    final VersionModel version) {
        final var userId = player.getUserId();
        final var playerId = player.getId();
        final var tenantId = tenant.getId();
        final var versionId = version.getId();

        final var client = clientModelFactory.create(userId, playerId, tenantId, versionId);
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

    public ClientMatchmakerRefModel createClientMatchmakerRef(final ClientModel client,
                                                              final MatchmakerModel matchmaker) {
        final var clientId = client.getId();
        final var matchmakerId = matchmaker.getId();
        final var clientMatchmakerRef = clientMatchmakerRefModelFactory.create(clientId, matchmakerId);
        final var syncClientMatchmakerRefRequest = new SyncClientMatchmakerRefRequest(clientMatchmakerRef);
        clientService.syncClientMatchmakerRef(syncClientMatchmakerRefRequest);
        return clientMatchmakerRef;
    }
}
