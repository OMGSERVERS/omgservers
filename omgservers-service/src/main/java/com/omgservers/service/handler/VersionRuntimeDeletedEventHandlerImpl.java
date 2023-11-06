package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
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
    public Uni<Boolean> handle(EventModel event) {
        final var body = (VersionRuntimeDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getDeletedVersionRuntime(tenantId, id)
                .flatMap(versionRuntime -> {
                    log.info("Version runtime was deleted, " +
                                    "key={}/{}, " +
                                    "versionId={}, " +
                                    "runtimeId={}",
                            tenantId,
                            id,
                            versionRuntime.getVersionId(),
                            versionRuntime.getRuntimeId());

                    return deleteRuntime(versionRuntime);
                })
                .replaceWith(true);
    }

    Uni<VersionRuntimeModel> getDeletedVersionRuntime(final Long tenantId, final Long id) {
        final var request = new GetVersionRuntimeRequest(tenantId, id, true);
        return tenantModule.getVersionService().getVersionRuntime(request)
                .map(GetVersionRuntimeResponse::getVersionRuntime);
    }

    Uni<Boolean> deleteRuntime(final VersionRuntimeModel versionRuntime) {
        final var runtimeId = versionRuntime.getRuntimeId();
        final var deleteRuntimeRequest = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(deleteRuntimeRequest)
                .map(DeleteRuntimeResponse::getDeleted);
    }
}
