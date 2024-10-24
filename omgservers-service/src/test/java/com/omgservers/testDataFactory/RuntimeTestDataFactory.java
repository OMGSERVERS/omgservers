package com.omgservers.testDataFactory;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
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
                                           final TenantDeploymentModel tenantDeployment,
                                           final LobbyModel lobby) {
        final var tenantId = tenant.getId();
        final var tenantDeploymentId = tenantDeployment.getId();

        final var runtimeId = lobby.getRuntimeId();

        final var config = RuntimeConfigDto.create();
        config.setLobbyConfig(new RuntimeConfigDto.LobbyConfigDto(lobby.getId()));
        final var runtime = runtimeModelFactory.create(runtimeId,
                tenantId,
                tenantDeploymentId,
                RuntimeQualifierEnum.LOBBY,
                config);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        runtimeService.execute(syncRuntimeRequest);
        return runtime;
    }

    public RuntimeModel createMatchRuntime(final TenantModel tenant,
                                           final TenantDeploymentModel tenantDeployment,
                                           final MatchmakerMatchModel match) {
        final var tenantId = tenant.getId();
        final var tenantDeploymentId = tenantDeployment.getId();

        final var runtimeId = match.getRuntimeId();

        final var config = RuntimeConfigDto.create();
        config.setMatchConfig(new RuntimeConfigDto.MatchConfigDto(match.getMatchmakerId(), match.getId()));
        final var runtime = runtimeModelFactory.create(runtimeId,
                tenantId,
                tenantDeploymentId,
                RuntimeQualifierEnum.MATCH,
                config);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        runtimeService.execute(syncRuntimeRequest);
        return runtime;
    }

    public RuntimeAssignmentModel createRuntimeAssignment(final RuntimeModel runtime,
                                                          final ClientModel client) {
        final var runtimeId = runtime.getId();
        final var clientId = client.getId();

        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId, clientId);
        final var syncRuntimeAssignmentRequest = new SyncRuntimeAssignmentRequest(runtimeAssignment);
        runtimeService.execute(syncRuntimeAssignmentRequest);
        return runtimeAssignment;
    }
}
