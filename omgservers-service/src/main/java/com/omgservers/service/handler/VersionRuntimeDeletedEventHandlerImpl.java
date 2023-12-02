package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionRuntimeDeletedEventBodyModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionRuntimeDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_RUNTIME_DELETED;

    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);
        
        final var body = (VersionRuntimeDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionRuntimeId = body.getId();

        return tenantModule.getShortcutService().getVersionRuntime(tenantId, versionRuntimeId)
                .flatMap(versionRuntime -> {
                    final var runtimeId = versionRuntime.getRuntimeId();
                    log.info("Version runtime was deleted, " +
                                    "versionRuntime={}/{}, " +
                                    "versionId={}, " +
                                    "runtimeId={}",
                            tenantId,
                            versionRuntimeId,
                            versionRuntime.getVersionId(),
                            runtimeId);
                    return runtimeModule.getShortcutService().deleteRuntime(runtimeId);
                })
                .replaceWith(true);
    }

    Uni<Boolean> deleteRuntime(final VersionRuntimeModel versionRuntime) {
        final var runtimeId = versionRuntime.getRuntimeId();
        final var deleteRuntimeRequest = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(deleteRuntimeRequest)
                .map(DeleteRuntimeResponse::getDeleted);
    }
}
