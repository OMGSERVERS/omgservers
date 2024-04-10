package com.omgservers.testDataFactory;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.module.runtime.impl.service.runtimeService.testInterface.RuntimeServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeTestDataFactory {

    final RuntimeServiceTestInterface runtimeService;

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final RuntimeModelFactory runtimeModelFactory;

    public RuntimeModel createLobbyRuntime(final TenantModel tenant,
                                           final VersionModel version,
                                           final LobbyModel lobby) {
        final var tenantId = tenant.getId();
        final var versionId = version.getId();

        final var runtimeId = lobby.getRuntimeId();

        final var config = RuntimeConfigModel.create();
        config.setLobbyConfig(new RuntimeConfigModel.LobbyConfig(lobby.getId()));
        final var runtime = runtimeModelFactory.create(runtimeId,
                tenantId,
                versionId,
                RuntimeQualifierEnum.LOBBY,
                config);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        runtimeService.syncRuntime(syncRuntimeRequest);
        return runtime;
    }

    public RuntimeModel createMatchRuntime(final TenantModel tenant,
                                           final VersionModel version,
                                           final MatchmakerMatchModel match) {
        final var tenantId = tenant.getId();
        final var versionId = version.getId();

        final var config = RuntimeConfigModel.create();
        config.setMatchConfig(new RuntimeConfigModel.MatchConfig(match.getMatchmakerId(), match.getId()));
        final var runtime = runtimeModelFactory.create(tenantId,
                versionId,
                RuntimeQualifierEnum.MATCH,
                config);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        runtimeService.syncRuntime(syncRuntimeRequest);
        return runtime;
    }

    public RuntimeAssignmentModel createRuntimeAssignment(final RuntimeModel runtime,
                                                          final ClientModel client) {
        final var runtimeId = runtime.getId();
        final var clientId = client.getId();

        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId, clientId);
        final var syncRuntimeAssignmentRequest = new SyncRuntimeAssignmentRequest(runtimeAssignment);
        runtimeService.syncRuntimeAssignment(syncRuntimeAssignmentRequest);
        return runtimeAssignment;
    }
}
