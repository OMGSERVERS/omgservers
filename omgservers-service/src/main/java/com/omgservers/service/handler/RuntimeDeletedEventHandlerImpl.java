package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.service.factory.JobModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getServers.GetServersOperation;
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

        return runtimeModule.getShortcutService().getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.info("Runtime was deleted, " +
                                    "id={}, " +
                                    "type={}, " +
                                    "version={}/{}",
                            runtime.getId(),
                            runtime.getType(),
                            runtime.getTenantId(),
                            runtime.getVersionId());

                    // TODO: cleanup container user
                    return deleteContainer(runtimeId)
                            .flatMap(wasContainerDeleted -> runtimeModule.getShortcutService()
                                    .deleteRuntimePermissions(runtimeId))
                            .flatMap(voidItem -> runtimeModule.getShortcutService()
                                    .deleteRuntimeCommands(runtimeId))
                            .flatMap(voidItem -> runtimeModule.getShortcutService()
                                    .deleteRuntimeGrants(runtimeId));
                })
                .replaceWith(true);
    }

    Uni<Boolean> deleteContainer(final Long runtimeId) {
        return systemModule.getShortcutService().findRuntimeContainer(runtimeId)
                .flatMap(container -> systemModule.getShortcutService().deleteContainer(container.getId()));
    }
}
