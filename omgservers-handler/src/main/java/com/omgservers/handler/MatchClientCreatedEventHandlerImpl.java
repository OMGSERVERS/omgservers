package com.omgservers.handler;

import com.omgservers.dto.matchmaker.GetMatchClientShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchClientShardedResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.dto.matchmaker.GetMatchShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.runtimeCommand.body.AddPlayerRuntimeCommandBodyModel;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
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
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CLIENT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchClientCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        return getMatchClient(matchmakerId, id)
                .flatMap(matchClient -> {
                    final var userId = matchClient.getUserId();
                    final var clientId = matchClient.getClientId();
                    return getClient(userId, clientId)
                            .flatMap(client -> {
                                final var matchId = matchClient.getMatchId();
                                return getMatch(matchmakerId, matchId)
                                        .flatMap(match -> {
                                            final var runtimeId = match.getRuntimeId();
                                            final var playerId = client.getPlayerId();
                                            final var runtimeCommandBody = new AddPlayerRuntimeCommandBodyModel(
                                                    userId,
                                                    playerId,
                                                    clientId);
                                            final var runtimeCommand = runtimeCommandModelFactory
                                                    .create(runtimeId, runtimeCommandBody);
                                            final var syncRuntimeCommandShardedRequest =
                                                    new SyncRuntimeCommandShardedRequest(runtimeCommand);
                                            return runtimeModule.getRuntimeShardedService()
                                                    .syncRuntimeCommand(syncRuntimeCommandShardedRequest);
                                        });
                            });

                })
                .replaceWith(true);
    }

    Uni<MatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var getMatchClientShardedRequest = new GetMatchClientShardedRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerShardedService().getMatchClient(getMatchClientShardedRequest)
                .map(GetMatchClientShardedResponse::getMatchClient);
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var getMatchShardedRequest = new GetMatchShardedRequest(matchmakerId, matchId);
        return matchmakerModule.getMatchmakerShardedService().getMatch(getMatchShardedRequest)
                .map(GetMatchShardedResponse::getMatch);
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientShardedRequest = new GetClientShardedRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientShardedRequest)
                .map(GetClientShardedResponse::getClient);
    }
}
