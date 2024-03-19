package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchRuntimeRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerMatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
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
public class MatchmakerMatchRuntimeRefDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;

    final RuntimeClientModelFactory runtimeClientModelFactory;
    final MessageModelFactory messageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_RUNTIME_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchRuntimeRefDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getMatchId();
        final var id = body.getId();

        return getMatchmakerMatchRuntimeRef(matchmakerId, matchId, id)
                .flatMap(matchmakerMatchRuntimeRef -> {
                    final var runtimeId = matchmakerMatchRuntimeRef.getRuntimeId();
                    log.info("Matchmaker match runtime ref was deleted, matchmakerId={}, matchId={}, runtimeId={} ",
                            matchmakerId, matchId, runtimeId);

                    return Uni.createFrom().voidItem();
                });
    }

    Uni<MatchmakerMatchRuntimeRefModel> getMatchmakerMatchRuntimeRef(final Long matchmakerId,
                                                                     final Long matchId,
                                                                     final Long id) {
        final var request = new GetMatchmakerMatchRuntimeRefRequest(matchmakerId, matchId, id);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatchRuntimeRef(request)
                .map(GetMatchmakerMatchRuntimeRefResponse::getMatchmakerMatchRuntimeRef);
    }
}
