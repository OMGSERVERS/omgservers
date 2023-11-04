package com.omgservers.handler;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.system.DeleteContainerRequest;
import com.omgservers.model.dto.system.DeleteContainerResponse;
import com.omgservers.model.dto.system.FindContainerRequest;
import com.omgservers.model.dto.system.FindContainerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.system.SystemModule;
import com.omgservers.factory.JobModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
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
    final TenantModule tenantModule;
    final SystemModule systemModule;

    final GetServersOperation getServersOperation;
    final JobModelFactory jobModelFactory;

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
                    log.info("Runtime was deleted, type={}, id={}, tenantId={}, versionId={}, versionId={}",
                            runtime.getType(),
                            runtime.getId(),
                            runtime.getTenantId(),
                            runtime.getVersionId(),
                            runtime.getVersionId());

                    // TODO: cleanup container user and runtime permission
                    return deleteContainer(runtimeId)
                            .flatMap(wasContainerDeleted -> {
                                if (runtime.getType().equals(RuntimeTypeEnum.VERSION)) {
                                    return deleteVersionRuntime(runtime);
                                } else {
                                    return Uni.createFrom().voidItem();
                                }
                            });
                })
                .replaceWith(true);
    }

    Uni<RuntimeModel> getDeletedRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id, true);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> deleteContainer(final Long runtimeId) {
        return findContainer(runtimeId)
                .flatMap(container -> {
                    final var request = new DeleteContainerRequest(container.getId());
                    return systemModule.getContainerService().deleteContainer(request)
                            .map(DeleteContainerResponse::getDeleted);
                });
    }

    Uni<ContainerModel> findContainer(final Long runtimeId) {
        final var request = new FindContainerRequest(runtimeId, ContainerQualifierEnum.RUNTIME, false);
        return systemModule.getContainerService().findContainer(request)
                .map(FindContainerResponse::getContainer);
    }

    Uni<Boolean> deleteVersionRuntime(RuntimeModel runtime) {
        return findVersionRuntime(runtime)
                .flatMap(versionRuntime -> {
                    final var request = new DeleteVersionRuntimeRequest(versionRuntime.getTenantId(),
                            versionRuntime.getId());
                    return tenantModule.getVersionService().deleteVersionRuntime(request)
                            .map(DeleteVersionRuntimeResponse::getDeleted);
                });
    }

    Uni<VersionRuntimeModel> findVersionRuntime(RuntimeModel runtime) {
        final var tenantId = runtime.getTenantId();
        final var versionId = runtime.getVersionId();
        final var runtimeId = runtime.getId();
        final var request = new FindVersionRuntimeRequest(tenantId, versionId, runtimeId);
        return tenantModule.getVersionService().findVersionRuntime(request)
                .map(FindVersionRuntimeResponse::getVersionRuntime);
    }
}
