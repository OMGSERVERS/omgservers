package com.omgservers.handler;

import com.omgservers.dto.internal.DeleteJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.operation.getServers.GetServersOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;
    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (RuntimeDeletedEventBodyModel) event.getBody();
        final var runtime = body.getRuntime();
        final var runtimeId = runtime.getId();

        log.info("Runtime was deleted, matchmakerId={}, matchId={}, mode={}",
                runtime.getMatchmakerId(),
                runtime.getMatchId(),
                runtime.getConfig().getModeConfig().getName());

        final var request = new DeleteJobRequest(runtimeId, runtimeId, JobQualifierEnum.RUNTIME);
        return systemModule.getJobService().deleteJob(request)
                .replaceWith(true);
    }
}
