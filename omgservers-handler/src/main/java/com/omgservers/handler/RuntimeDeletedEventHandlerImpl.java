package com.omgservers.handler;

import com.omgservers.dto.internal.DeleteJobRequest;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.module.runtime.RuntimeModule;
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

    final RuntimeModule runtimeModule;
    final SystemModule systemModule;

    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (RuntimeDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        return getDeletedRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.info("Runtime was deleted, id={}, matchmakerId={}, matchId={}, mode={}",
                            runtime.getId(),
                            runtime.getMatchmakerId(),
                            runtime.getMatchId(),
                            runtime.getConfig().getModeConfig().getName());
                    return deleteRuntimeJob(runtimeId);
                });
    }

    Uni<RuntimeModel> getDeletedRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id, true);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> deleteRuntimeJob(final Long runtimeId) {
        final var request = new DeleteJobRequest(runtimeId, runtimeId, JobQualifierEnum.RUNTIME);
        return systemModule.getJobService().deleteJob(request)
                .replaceWith(true);
    }
}
