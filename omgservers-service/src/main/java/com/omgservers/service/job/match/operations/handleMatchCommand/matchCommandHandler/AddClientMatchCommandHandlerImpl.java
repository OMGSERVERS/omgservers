package com.omgservers.service.job.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
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

import java.util.Objects;

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
                        .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                        .invoke(client -> {
                            if (Objects.isNull(client)) {
                                log.warn("Add client match command failed, client doesn't exist anymore, " +
                                                "{}/{}, matchmakerId={}, matchId={}",
                                        userId, clientId, matchmakerId, matchId);
                            }
                        })
                        .onItem().ifNotNull().transformToUni(client -> {
                            final var runtimeId = match.getRuntimeId();
                            final var playerId = client.getPlayerId();
                            return syncRuntimeGrant(runtimeId, userId, clientId)
                                    .call(ignored -> getPlayer(client)
                                            .flatMap(player -> syncAddClientRuntimeCommand(runtimeId,
                                                    player,
                                                    client)))
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

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId, false);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var request = new GetClientRequest(userId, clientId, false);
        return userModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> syncRuntimeGrant(Long runtimeId, Long userId, Long clientId) {
        final var runtimeGrant = runtimeGrantModelFactory.create(
                runtimeId,
                userId,
                clientId,
                RuntimeGrantTypeEnum.MATCH_CLIENT);
        final var request = new SyncRuntimeGrantRequest(runtimeGrant);
        return runtimeModule.getRuntimeService().syncRuntimeGrant(request)
                .map(SyncRuntimeGrantResponse::getCreated);
    }

    Uni<PlayerModel> getPlayer(final ClientModel client) {
        final var userId = client.getUserId();
        final var playerId = client.getPlayerId();
        final var request = new GetPlayerRequest(userId, playerId, false);
        return userModule.getPlayerService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }

    Uni<Boolean> syncAddClientRuntimeCommand(final Long runtimeId,
                                             final PlayerModel player,
                                             final ClientModel client) {
        final var userId = client.getUserId();
        final var clientId = client.getId();
        final var runtimeCommandBody = new AddClientRuntimeCommandBodyModel(userId,
                clientId,
                player.getAttributes(),
                player.getObject());
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
