package com.omgservers.handler;

import com.omgservers.dto.internal.DeleteJobRequest;
import com.omgservers.dto.internal.DeleteJobResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;
    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchDeletedEventBodyModel) event.getBody();
        final var match = body.getMatch();
        final var matchmakerId = match.getMatchmakerId();
        final var matchId = match.getId();
        final var runtimeId = match.getRuntimeId();

        log.info("Match was deleted, matchId={}, mode={}, matchmakerId={}",
                matchId, match.getConfig().getModeConfig().getName(), matchmakerId);

        return deleteRuntime(runtimeId)
                .flatMap(runtimeWasDeleted -> deleteMatchJob(matchmakerId, matchId))
                .replaceWith(true);
    }

    Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }

    Uni<Boolean> deleteMatchJob(final Long matchmakerId, final Long matchId) {
        final var request = new DeleteJobRequest(matchmakerId, matchId, JobQualifierEnum.MATCH);
        return systemModule.getJobService().deleteJob(request)
                .map(DeleteJobResponse::getDeleted);
    }
}
