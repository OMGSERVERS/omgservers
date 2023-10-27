package com.omgservers.handler;

import com.omgservers.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchCommandModelFactory;
import com.omgservers.module.runtime.RuntimeModule;
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

    final MatchCommandModelFactory matchCommandModelFactory;
    final RuntimeGrantModelFactory runtimeGrantModelFactory;
    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CLIENT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        final var body = (MatchClientCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getMatchId();
        final var id = body.getId();

        return getMatchClient(matchmakerId, id)
                .flatMap(matchClient -> {
                    final var userId = matchClient.getUserId();
                    final var clientId = matchClient.getClientId();

                    log.info("Match client was created, matchmakerId={}, matchId={}, userId={}, clientId={}",
                            matchmakerId, matchId, userId, clientId);

                    return syncAddClientMatchCommand(matchmakerId,
                            matchId,
                            userId,
                            clientId);
                })
                .replaceWith(true);
    }

    Uni<MatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchClient(request)
                .map(GetMatchClientResponse::getMatchClient);
    }

    Uni<Boolean> syncAddClientMatchCommand(final Long matchmakerId,
                                           final Long matchId,
                                           final Long userId,
                                           final Long clientId) {
        final var matchCommandBody = new AddClientMatchCommandBodyModel(userId, clientId);
        final var matchCommandModel = matchCommandModelFactory.create(matchmakerId, matchId, matchCommandBody);
        final var syncMatchCommandRequest = new SyncMatchCommandRequest(matchCommandModel);
        return matchmakerModule.getMatchmakerService().syncMatchCommand(syncMatchCommandRequest)
                .map(SyncMatchCommandResponse::getCreated);
    }
}
