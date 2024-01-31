package com.omgservers.service.handler.tenant;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.service.factory.VersionMatchmakerModelFactory;
import com.omgservers.service.factory.VersionRuntimeModelFactory;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final VersionMatchmakerModelFactory versionMatchmakerModelFactory;
    final VersionRuntimeModelFactory versionRuntimeModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_DELETED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getId();

        return tenantModule.getShortcutService().getVersion(tenantId, versionId)
                .flatMap(version -> {
                    log.info("Version was deleted, {}/{}, stageId={}, modes={}",
                            tenantId,
                            versionId,
                            version.getStageId(),
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList());

                    return tenantModule.getShortcutService().deleteVersionMatchmakers(tenantId, versionId)
                            .flatMap(wasVersionRuntimesDeleted -> tenantModule.getShortcutService()
                                    .deleteVersionRuntimes(tenantId, versionId));
                })
                .replaceWith(true);
    }
}
