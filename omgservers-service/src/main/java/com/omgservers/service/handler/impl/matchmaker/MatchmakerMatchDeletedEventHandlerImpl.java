package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchResponse;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsRequest;
import com.omgservers.schema.module.matchmaker.ViewMatchmakerMatchAssignmentsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    log.info("Deleted, {}", match);

                    final var runtimeId = match.getRuntimeId();
                    return deleteRuntime(runtimeId)
                            .flatMap(voidItem -> deleteMatchmakerMatchAssignments(matchmakerId, matchId));
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchModel> getMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchmakerMatchRequest(matchmakerId, matchId);
        return matchmakerModule.getService().execute(request)
                .map(GetMatchmakerMatchResponse::getMatchmakerMatch);
    }

    Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getService().execute(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }

    Uni<Void> deleteMatchmakerMatchAssignments(final Long matchmakerId, final Long matchmakerMatchId) {
        return viewMatchmakerMatchAssignments(matchmakerId, matchmakerMatchId)
                .flatMap(matchmakerMatchAssignments -> Multi.createFrom().iterable(matchmakerMatchAssignments)
                        .onItem().transformToUniAndConcatenate(matchmakerMatchAssignment ->
                                deleteMatchAssignment(matchmakerId, matchmakerMatchAssignment.getId())
                                        .onFailure()
                                        .recoverWithUni(t -> {
                                            log.warn("Failed to delete, {}, {}:{}",
                                                    matchmakerMatchAssignment,
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return Uni.createFrom().item(Boolean.FALSE);
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<MatchmakerMatchAssignmentModel>> viewMatchmakerMatchAssignments(final Long matchmakerId,
                                                                             final Long matchId) {
        final var request = new ViewMatchmakerMatchAssignmentsRequest(matchmakerId, matchId);
        return matchmakerModule.getService().execute(request)
                .map(ViewMatchmakerMatchAssignmentsResponse::getMatchmakerMatchAssignments);
    }

    Uni<Boolean> deleteMatchAssignment(final Long matchmakerId, final Long id) {
        final var request = new DeleteMatchmakerMatchAssignmentRequest(matchmakerId, id);
        return matchmakerModule.getService().execute(request)
                .map(DeleteMatchmakerMatchAssignmentResponse::getDeleted);
    }
}
