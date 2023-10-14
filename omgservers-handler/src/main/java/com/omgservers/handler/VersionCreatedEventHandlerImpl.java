package com.omgservers.handler;

import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionModel;
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
public class VersionCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (VersionCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersion(tenantId, id)
                .invoke(version -> {
                    log.info("Version was created, versionId={}, modes={}, files={}, tenantId={}, stageId={}",
                            id,
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList(),
                            version.getSourceCode().getFiles().size(),
                            tenantId,
                            version.getStageId());
                })
                .replaceWith(true);
    }

    Uni<VersionModel> getVersion(Long tenantId, Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }
}
