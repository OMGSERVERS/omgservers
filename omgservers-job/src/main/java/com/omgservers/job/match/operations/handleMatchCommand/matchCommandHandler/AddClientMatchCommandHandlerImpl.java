package com.omgservers.job.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.AssignRuntimeResponse;
import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.job.match.operations.handleMatchCommand.MatchCommandHandler;
import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeGrantModelFactory;
import com.omgservers.module.user.UserModule;
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

        return getMatch(matchmakerId, matchId)
                .flatMap(match -> getClient(userId, clientId)
                        .flatMap(client -> {
                            final var runtimeId = match.getRuntimeId();
                            final var playerId = client.getPlayerId();
                            return syncRuntimeGrant(runtimeId, userId, clientId)
                                    .call(ignored -> syncAddClientRuntimeCommand(runtimeId,
                                            userId,
                                            playerId,
                                            clientId))
                                    .call(ignored -> assignRuntime(runtimeId, client));
                        })
                        .onFailure(ServerSideNotFoundException.class)
                        .invoke(t -> log.warn("Add client match command failed, client doesn't exist anymore, " +
                                        "userId={}, clientId={}, matchmakerId={}, matchId={}",
                                userId, clientId, matchmakerId, matchId))
                )
                .replaceWithVoid();
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var request = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> syncRuntimeGrant(Long runtimeId, Long userId, Long clientId) {
        final var runtimeGrant = runtimeGrantModelFactory.create(
                runtimeId,
                userId,
                clientId,
                RuntimeGrantTypeEnum.CLIENT);
        final var request = new SyncRuntimeGrantRequest(runtimeGrant);
        return runtimeModule.getRuntimeService().syncRuntimeGrant(request)
                .map(SyncRuntimeGrantResponse::getCreated);
    }

    Uni<Boolean> syncAddClientRuntimeCommand(final Long runtimeId,
                                             final Long userId,
                                             final Long playerId,
                                             final Long clientId) {
        final var runtimeCommandBody = new AddClientRuntimeCommandBodyModel(userId, playerId, clientId);
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
