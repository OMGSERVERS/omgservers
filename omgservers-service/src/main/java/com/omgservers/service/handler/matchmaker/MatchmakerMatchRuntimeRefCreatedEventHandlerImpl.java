package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerMatchRuntimeRefCreatedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.PrepareMatchMatchmakerCommandBodyModel;
import com.omgservers.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.service.factory.MatchmakerCommandModelFactory;
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
public class MatchmakerMatchRuntimeRefCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_RUNTIME_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchRuntimeRefCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getMatchId();
        final var id = body.getId();

        return getMatchmakerMatchRuntimeRef(matchmakerId, matchId, id)
                .flatMap(matchmakerMatchRuntimeRef -> {
                    final var runtimeId = matchmakerMatchRuntimeRef.getRuntimeId();
                    log.info("Matchmaker match runtime ref was created, matchmakerId={}, matchId={}, runtimeId={} ",
                            matchmakerId, matchId, runtimeId);

                    return syncPrepareMatchMatchmakerCommand(matchmakerId, matchId)
                            .replaceWithVoid();
                });
    }

    Uni<MatchmakerMatchRuntimeRefModel> getMatchmakerMatchRuntimeRef(final Long matchmakerId,
                                                                     final Long matchId,
                                                                     final Long id) {
        final var request = new GetMatchmakerMatchRuntimeRefRequest(matchmakerId, matchId, id);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatchRuntimeRef(request)
                .map(GetMatchmakerMatchRuntimeRefResponse::getMatchmakerMatchRuntimeRef);
    }

    Uni<Boolean> syncPrepareMatchMatchmakerCommand(final Long matchmakerId, final Long matchId) {
        final var commandBody = new PrepareMatchMatchmakerCommandBodyModel(matchId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}
