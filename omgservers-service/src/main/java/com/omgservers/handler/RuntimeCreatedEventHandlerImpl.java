package com.omgservers.handler;

import com.omgservers.dto.internal.SyncContainerRequest;
import com.omgservers.dto.internal.SyncContainerResponse;
import com.omgservers.dto.internal.SyncJobRequest;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.script.SyncScriptRequest;
import com.omgservers.dto.script.SyncScriptResponse;
import com.omgservers.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.model.container.ContainerTypeEnum;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.script.ScriptConfigModel;
import com.omgservers.model.script.ScriptTypeEnum;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.script.factory.ScriptModelFactory;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.ContainerModelFactory;
import com.omgservers.module.system.factory.JobModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.factory.VersionRuntimeModelFactory;
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
    final TenantModule tenantModule;

    final VersionRuntimeModelFactory versionRuntimeModelFactory;
    final ContainerModelFactory containerModelFactory;
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
                .call(runtime -> {
                    log.info("Runtime was created, id={}, type={}",
                            runtime.getId(),
                            runtime.getType());

                    return syncByType(runtime)
                            .replaceWithVoid();
                })
                .call(this::syncRuntimeJob)
                .replaceWith(true);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id, false);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncByType(final RuntimeModel runtime) {
        return switch (runtime.getType()) {
            case EMBEDDED_GLOBAL_SCRIPT -> syncVersionRuntime(runtime)
                    .flatMap(wasVersionRuntimeCreated -> syncScript(runtime, ScriptTypeEnum.GLOBAL));
            case EMBEDDED_MATCH_SCRIPT -> syncScript(runtime, ScriptTypeEnum.MATCH);
            case CONTAINER_GLOBAL_SCRIPT -> syncVersionRuntime(runtime)
                    .flatMap(wasVersionRuntimeCreated -> syncContainer(runtime, ContainerTypeEnum.GLOBAL));
            case CONTAINER_MATCH_SCRIPT -> syncContainer(runtime, ContainerTypeEnum.MATCH);
        };
    }

    Uni<Boolean> syncVersionRuntime(final RuntimeModel runtime) {
        final var versionRuntime = versionRuntimeModelFactory.create(runtime.getTenantId(),
                runtime.getVersionId(),
                runtime.getId());
        final var request = new SyncVersionRuntimeRequest(versionRuntime);
        return tenantModule.getVersionService().syncVersionRuntime(request)
                .map(SyncVersionRuntimeResponse::getCreated);
    }

    Uni<Boolean> syncScript(final RuntimeModel runtime, final ScriptTypeEnum type) {
        final var runtimeId = runtime.getId();
        final var tenantId = runtime.getTenantId();
        final var versionId = runtime.getVersionId();
        final var config = new ScriptConfigModel();
        final var scriptId = runtime.getConfig().getScriptConfig().getScriptId();
        final var script = scriptModelFactory.create(scriptId, tenantId, versionId, runtimeId, type, config);
        final var request = new SyncScriptRequest(script);
        return scriptModule.getScriptService().syncScript(request)
                .map(SyncScriptResponse::getCreated);
    }

    Uni<Boolean> syncContainer(final RuntimeModel runtime, final ContainerTypeEnum type) {
        final var runtimeId = runtime.getId();
        final var tenantId = runtime.getTenantId();
        final var versionId = runtime.getVersionId();
        final var containerId = runtime.getConfig().getContainerConfig().getContainerId();
        final var container = containerModelFactory.create(containerId, tenantId, versionId, runtimeId, type);
        final var request = new SyncContainerRequest(container);
        return systemModule.getContainerService().syncContainer(request)
                .map(SyncContainerResponse::getCreated);
    }

    Uni<JobModel> syncRuntimeJob(final RuntimeModel runtime) {
        final var shardKey = runtime.getId();
        final var entityId = runtime.getId();
        final var job = jobModelFactory.create(shardKey, entityId, JobQualifierEnum.RUNTIME);
        final var request = new SyncJobRequest(job);
        return systemModule.getJobService().syncJob(request)
                .replaceWith(job);
    }
}
