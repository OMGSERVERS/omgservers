package com.omgservers.service.handler;

import com.omgservers.model.dto.matchmaker.GetMatchRequest;
import com.omgservers.model.dto.matchmaker.GetMatchResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.match.MatchModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final SystemModule systemModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var matchId = body.getId();

        return getDeletedMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var runtimeId = match.getRuntimeId();
                    log.info("Match was deleted, " +
                                    "matchId={}, " +
                                    "mode={}, " +
                                    "matchmakerId={}, " +
                                    "runtimeId={}",
                            matchId,
                            match.getConfig().getModeConfig().getName(),
                            matchmakerId,
                            runtimeId);
                    return deleteRuntime(runtimeId)
                            .flatMap(runtimeWasDeleted -> deleteMatchJob(matchmakerId, matchId));
                })
                .replaceWith(true);
    }

    Uni<MatchModel> getDeletedMatch(final Long matchmakerId, final Long matchId) {
        final var request = new GetMatchRequest(matchmakerId, matchId, true);
        return matchmakerModule.getMatchmakerService().getMatch(request)
                .map(GetMatchResponse::getMatch);
    }

    Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }

    Uni<Boolean> deleteMatchJob(final Long matchmakerId, final Long matchId) {
        return findJob(matchmakerId, matchId)
                .flatMap(job -> {
                    final var request = new DeleteJobRequest(job.getId());
                    return systemModule.getJobService().deleteJob(request)
                            .map(DeleteJobResponse::getDeleted);
                });
    }

    Uni<JobModel> findJob(final Long matchmakerId, final Long matchId) {
        final var request = new FindJobRequest(matchmakerId, matchId, JobQualifierEnum.MATCH);
        return systemModule.getJobService().findJob(request)
                .map(FindJobResponse::getJob);
    }
}
