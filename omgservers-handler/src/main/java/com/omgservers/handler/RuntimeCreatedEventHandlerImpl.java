package com.omgservers.handler;

import com.omgservers.dto.internal.SyncJobRequest;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.model.script.ScriptTypeEnum;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.script.factory.ScriptModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.JobModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final SystemModule systemModule;
    final ScriptModule scriptModule;

    final ScriptModelFactory scriptModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (RuntimeCreatedEventBodyModel) event.getBody();
        final var id = body.getId();
        return getRuntime(id)
                .call(this::syncGame)
                .replaceWith(true);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Void> syncGame(final RuntimeModel runtime) {
        return switch (runtime.getType()) {
            case SCRIPT -> syncScript(runtime)
                    .flatMap(script -> syncJob(runtime))
                    .replaceWithVoid();
        };
    }

    Uni<ScriptModel> syncScript(final RuntimeModel runtime) {
        final var tenantId = runtime.getTenantId();
        final var versionId = runtime.getVersionId();
        final var type = ScriptTypeEnum.RUNTIME;
        final var config = ScriptConfigModel.builder()
                .matchmakerId(runtime.getMatchmakerId())
                .matchId(runtime.getMatchId())
                .runtimeId(runtime.getId())
                .build();
        final var script = scriptModelFactory.create(runtime.getScriptId(), tenantId, versionId, type, config);
        final var request = new SyncScriptRequest(script);
        return scriptModule.getScriptService().syncScript(request)
                .replaceWith(script);
    }

    Uni<JobModel> syncJob(final RuntimeModel runtime) {
        final var shardKey = runtime.getId();
        final var entityId = runtime.getId();
        final var job = jobModelFactory.create(shardKey, entityId, JobQualifierEnum.RUNTIME);
        final var request = new SyncJobRequest(job);
        return systemModule.getJobService().syncJob(request)
                .replaceWith(job);
    }
}
