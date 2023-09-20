package com.omgservers.handler;

import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.dto.matchmaker.GetMatchRequest;
import com.omgservers.dto.matchmaker.GetMatchResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeGrantModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchClientCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final GatewayModule gatewayModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final RuntimeGrantModelFactory runtimeGrantModelFactory;
    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CLIENT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchClientCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();
        return collectMetadata(matchmakerId, id)
                .flatMap(metadata -> {
                    final var client = metadata.client;
                    final var runtimeId = metadata.match.getRuntimeId();
                    return assignRuntime(client, runtimeId)
                            .flatMap(voidItem -> syncRuntimeGrant(runtimeId, client.getUserId(), client.getId()))
                            .flatMap(voidItem -> syncAddClientCommand(runtimeId, client));
                })
                .replaceWith(true);
    }

    Uni<Metadata> collectMetadata(final Long matchmakerId, final Long matchClientId) {
        return getMatchClient(matchmakerId, matchClientId)
                .flatMap(matchClient -> {
                    final var userId = matchClient.getUserId();
                    final var clientId = matchClient.getClientId();
                    final var matchId = matchClient.getMatchId();
                    return getMatch(matchmakerId, matchId)
                            .flatMap(match -> getClient(userId, clientId)
                                    .map(client -> new Metadata(matchClient, match, client)));
                });
    }

    Uni<MatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var getMatchClientShardedRequest = new GetMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchClient(getMatchClientShardedRequest)
                .map(GetMatchClientResponse::getMatchClient);
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var getMatchShardedRequest = new GetMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerService().getMatch(getMatchShardedRequest)
                .map(GetMatchResponse::getMatch);
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientShardedRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientShardedRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<Void> assignRuntime(ClientModel client, Long runtimeId) {
        final var server = client.getServer();
        final var connectionId = client.getConnectionId();
        final var assignedRuntime = new AssignedRuntimeModel(runtimeId);
        final var assignRuntimeRequest = new AssignRuntimeRequest(server, connectionId, assignedRuntime);
        return gatewayModule.getGatewayService().assignRuntime(assignRuntimeRequest);
    }

    Uni<Void> syncRuntimeGrant(Long runtimeId, Long userId, Long clientId) {
        final var runtimeGrant = runtimeGrantModelFactory.create(
                runtimeId,
                userId,
                clientId,
                RuntimeGrantTypeEnum.CLIENT);
        final var request = new SyncRuntimeGrantRequest(runtimeGrant);
        return runtimeModule.getRuntimeService().syncRuntimeGrant(request)
                .replaceWithVoid();
    }

    Uni<Void> syncAddClientCommand(Long runtimeId, ClientModel client) {
        final var clientId = client.getId();
        final var userId = client.getUserId();
        final var playerId = client.getPlayerId();
        final var runtimeCommandBody = new AddClientRuntimeCommandBodyModel(userId, playerId, clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        final var syncRuntimeCommandShardedRequest = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(syncRuntimeCommandShardedRequest)
                .replaceWithVoid();
    }

    record Metadata(MatchClientModel matchClient, MatchModel match, ClientModel client) {
    }
}
