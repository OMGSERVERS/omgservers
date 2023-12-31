package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchClientCreatedEventBodyModel;
import com.omgservers.model.matchCommand.body.AddClientMatchCommandBodyModel;
import com.omgservers.service.factory.MatchCommandModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
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
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchClientCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getMatchId();
        final var matchClientId = body.getId();

        return matchmakerModule.getShortcutService().getMatchClient(matchmakerId, matchClientId)
                .flatMap(matchClient -> {
                    final var userId = matchClient.getUserId();
                    final var clientId = matchClient.getClientId();

                    log.info("Match client was created, match={}/{}, client={}/{}",
                            matchmakerId, matchId, userId, clientId);

                    return syncAddClientMatchCommand(matchmakerId,
                            matchId,
                            userId,
                            clientId);
                })
                .replaceWith(true);
    }

    Uni<Boolean> syncAddClientMatchCommand(final Long matchmakerId,
                                           final Long matchId,
                                           final Long userId,
                                           final Long clientId) {
        final var matchCommandBody = new AddClientMatchCommandBodyModel(userId, clientId);
        final var matchCommand = matchCommandModelFactory.create(matchmakerId, matchId, matchCommandBody);
        return matchmakerModule.getShortcutService().syncMatchCommand(matchCommand);
    }
}
