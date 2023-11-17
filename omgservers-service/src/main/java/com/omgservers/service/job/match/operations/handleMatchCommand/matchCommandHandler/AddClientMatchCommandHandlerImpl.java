package com.omgservers.service.job.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.factory.RuntimeGrantModelFactory;
import com.omgservers.service.job.match.operations.handleMatchCommand.MatchCommandHandler;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddClientMatchCommandHandlerImpl implements MatchCommandHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final GatewayModule gatewayModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final RuntimeGrantModelFactory runtimeGrantModelFactory;

    @Override
    public MatchCommandQualifierEnum getQualifier() {
        return MatchCommandQualifierEnum.ADD_CLIENT;
    }

    @Override
    public Uni<Void> handle(MatchCommandModel matchCommand) {
        final var matchmakerId = matchCommand.getMatchmakerId();
        final var matchId = matchCommand.getMatchId();

        final var body = (AddClientMatchCommandBodyModel) matchCommand.getBody();
        final var userId = body.getUserId();
        final var clientId = body.getClientId();

        return matchmakerModule.getShortcutService().getMatch(matchmakerId, matchId)
                .flatMap(match -> userModule.getShortcutService().getClient(userId, clientId)
                        .flatMap(client -> {
                            final var runtimeId = match.getRuntimeId();
                            return syncRuntimeGrant(runtimeId, userId, clientId)
                                    .call(ignored -> syncAddClientRuntimeCommand(runtimeId, client))
                                    .call(ignored -> assignRuntime(runtimeId, client))
                                    .invoke(voidItem -> {
                                        log.info(
                                                "Client was added into match, " +
                                                        "client={}/{}, " +
                                                        "match={}/{}, " +
                                                        "modeName={}",
                                                userId,
                                                clientId,
                                                matchmakerId,
                                                matchId,
                                                match.getConfig().getModeConfig().getName());
                                    });
                        })
                )
                .replaceWithVoid();
    }

    Uni<Boolean> syncRuntimeGrant(Long runtimeId, Long userId, Long clientId) {
        final var runtimeGrant = runtimeGrantModelFactory.create(
                runtimeId,
                userId,
                clientId,
                RuntimeGrantTypeEnum.MATCH_CLIENT);
        return runtimeModule.getShortcutService().syncRuntimeGrant(runtimeGrant);
    }

    Uni<Boolean> syncAddClientRuntimeCommand(final Long runtimeId,
                                             final ClientModel client) {
        final var userId = client.getUserId();
        final var clientId = client.getId();
        final var runtimeCommandBody = new AddClientRuntimeCommandBodyModel(userId, clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        final var syncRuntimeCommandShardedRequest = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(syncRuntimeCommandShardedRequest)
                .map(SyncRuntimeCommandResponse::getCreated);
    }

    Uni<Boolean> assignRuntime(final Long runtimeId, final ClientModel client) {
        final var server = client.getServer();
        final var connectionId = client.getConnectionId();
        final var assignedRuntime = new AssignedRuntimeModel(runtimeId);
        final var assignRuntimeRequest = new AssignRuntimeRequest(server, connectionId, assignedRuntime);
        return gatewayModule.getGatewayService().assignRuntime(assignRuntimeRequest)
                .map(AssignRuntimeResponse::getAssigned);
    }
}
