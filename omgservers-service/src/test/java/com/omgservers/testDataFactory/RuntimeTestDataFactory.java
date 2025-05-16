package com.omgservers.testDataFactory;

import com.omgservers.schema.message.body.RuntimeCreatedMessageBodyDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.shard.runtime.service.testInterface.RuntimeServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeTestDataFactory {

    final RuntimeServiceTestInterface runtimeService;

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final RuntimeMessageModelFactory runtimeMessageModelFactory;

    final RuntimeModelFactory runtimeModelFactory;

    public RuntimeModel createLobbyRuntime(final DeploymentModel deployment,
                                           final LobbyModel lobby) {
        final var deploymentId = deployment.getId();
        final var runtimeId = lobby.getRuntimeId();

        final var config = RuntimeConfigDto.create(new TenantVersionConfigDto());
        config.setLobby(new RuntimeConfigDto.LobbyConfigDto(lobby.getId()));
        final var runtime = runtimeModelFactory.create(runtimeId,
                deploymentId,
                RuntimeQualifierEnum.LOBBY,
                config);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        runtimeService.execute(syncRuntimeRequest);
        return runtime;
    }

    public RuntimeModel createMatchRuntime(final DeploymentModel deployment,
                                           final MatchModel match) {
        final var deploymentId = deployment.getId();

        final var runtimeId = match.getRuntimeId();
        final var mode = match.getConfig().getMode();

        final var config = RuntimeConfigDto.create(new TenantVersionConfigDto());
        config.setMatch(new RuntimeConfigDto.MatchConfigDto(match.getMatchmakerId(), match.getId(), mode));
        final var runtime = runtimeModelFactory.create(runtimeId,
                deploymentId,
                RuntimeQualifierEnum.MATCH,
                config);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        runtimeService.execute(syncRuntimeRequest);
        return runtime;
    }

    public RuntimeMessageModel createRuntimeCreatedRuntimeMessage(final RuntimeModel runtime) {
        final var messageBody = new RuntimeCreatedMessageBodyDto(runtime.getConfig());
        final var runtimeMessage = runtimeMessageModelFactory.create(runtime.getId(), messageBody);

        final var syncRuntimeMessageRequest = new SyncRuntimeMessageRequest(runtimeMessage);
        runtimeService.execute(syncRuntimeMessageRequest);
        return runtimeMessage;
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
