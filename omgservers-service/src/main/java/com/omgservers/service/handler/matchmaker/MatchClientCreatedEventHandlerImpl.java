package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.service.factory.MatchCommandModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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

    final MatchCommandModelFactory matchCommandModelFactory;
    final RuntimeClientModelFactory runtimeClientModelFactory;
    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_CLIENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchClientCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getMatchId();
        final var matchClientId = body.getId();

        return getMatchClient(matchmakerId, matchClientId)
                .flatMap(matchClient -> {
                    final var clientId = matchClient.getClientId();

                    log.info("Match client was created, id={}, match={}/{}, clientId={}",
                            matchClient.getId(), matchmakerId, matchId, clientId);

                    return syncAddClientMatchCommand(matchmakerId,
                            matchId,
                            clientId);
                })
                .replaceWithVoid();
    }

    Uni<MatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchClient(request)
                .map(GetMatchClientResponse::getMatchClient);
    }

    Uni<Boolean> syncAddClientMatchCommand(final Long matchmakerId,
                                           final Long matchId,
                                           final Long clientId) {
        final var matchCommandBody = new AddClientMatchCommandBodyModel(clientId);
        final var matchCommand = matchCommandModelFactory.create(matchmakerId, matchId, matchCommandBody);
        return syncMatchCommand(matchCommand);
    }

    Uni<Boolean> syncMatchCommand(final MatchCommandModel matchCommand) {
        final var request = new SyncMatchCommandRequest(matchCommand);
        return matchmakerModule.getMatchmakerService().syncMatchCommand(request)
                .map(SyncMatchCommandResponse::getCreated);
    }
}
