package com.omgservers.service.handler;

import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionRuntimeCreatedEventBodyModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.factory.RuntimeModelFactory;
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
public class VersionRuntimeCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final RuntimeModelFactory runtimeModelFactory;

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
                .flatMap(versionRuntime -> {
                    log.info("Version runtime was created, " +
                                    "versionRuntime={}/{}, " +
                                    "versionId={}, " +
                                    "runtimeId={}",
                            tenantId,
                            id,
                            versionRuntime.getVersionId(),
                            versionRuntime.getRuntimeId());

                    return syncRuntime(versionRuntime);
                })
                .replaceWith(true);
    }

    Uni<VersionRuntimeModel> getVersionRuntime(final Long tenantId, final Long id) {
        final var request = new GetVersionRuntimeRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionRuntime(request)
                .map(GetVersionRuntimeResponse::getVersionRuntime);
    }

    Uni<Boolean> syncRuntime(final VersionRuntimeModel versionRuntime) {
        final var runtimeConfig = new RuntimeConfigModel();
        final var runtime = runtimeModelFactory.create(
                versionRuntime.getRuntimeId(),
                versionRuntime.getTenantId(),
                versionRuntime.getVersionId(),
                RuntimeTypeEnum.VERSION,
                runtimeConfig);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        return runtimeModule.getRuntimeService().syncRuntime(syncRuntimeRequest)
                .map(SyncRuntimeResponse::getCreated);
    }
}
