package com.omgservers.handler;

import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionRuntimeCreatedEventBodyModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionRuntimeCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_RUNTIME_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (VersionRuntimeCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionRuntime(tenantId, id)
                .invoke(versionRuntime -> log.info("Version runtime was created, " +
                                "id={}, " +
                                "tenantId={}, " +
                                "versionId={}, " +
                                "runtimeId={}",
                        id,
                        tenantId,
                        versionRuntime.getVersionId(),
                        versionRuntime.getRuntimeId()))
                .replaceWith(true);
    }

    Uni<VersionRuntimeModel> getVersionRuntime(final Long tenantId, final Long id) {
        final var request = new GetVersionRuntimeRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionRuntime(request)
                .map(GetVersionRuntimeResponse::getVersionRuntime);
    }
}
