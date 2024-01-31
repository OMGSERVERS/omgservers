package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionModel;
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
public class VersionCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final VersionMatchmakerModelFactory versionMatchmakerModelFactory;
    final VersionRuntimeModelFactory versionRuntimeModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return tenantModule.getShortcutService().getVersion(tenantId, id)
                .flatMap(version -> {
                    log.info("Version was created, version={}/{}, stageId={}, modes={}, files={}",
                            tenantId,
                            id,
                            version.getStageId(),
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList(),
                            version.getSourceCode().getFiles().size());

                    return syncVersionMatchmaker(version)
                            .flatMap(wasVersionMatchmakerCreated -> syncVersionRuntime(version));
                })
                .replaceWith(true);
    }

    Uni<Boolean> syncVersionMatchmaker(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var matchmakerId = generateIdOperation.generateId();

        final var versionMatchmaker = versionMatchmakerModelFactory.create(tenantId,
                versionId,
                matchmakerId);
        final var request = new SyncVersionMatchmakerRequest(versionMatchmaker);
        return tenantModule.getVersionService().syncVersionMatchmaker(request)
                .map(SyncVersionMatchmakerResponse::getCreated);
    }

    Uni<Boolean> syncVersionRuntime(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var runtimeId = generateIdOperation.generateId();

        final var versionRuntime = versionRuntimeModelFactory.create(tenantId,
                versionId,
                runtimeId);
        final var request = new SyncVersionRuntimeRequest(versionRuntime);
        return tenantModule.getVersionService().syncVersionRuntime(request)
                .map(SyncVersionRuntimeResponse::getCreated);
    }
}
