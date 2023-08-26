package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.versionModule.GetVersionRoutedRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionRoutedRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import com.omgservers.exception.ServerSideClientErrorException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionStatusEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionCreatedEventHandlerImpl implements EventHandler {

    final VersionModule versionModule;
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

        return getVersion(id)
                .flatMap(version -> getStage(tenantId, body.getStageId())
                        .flatMap(stage -> deployVersion(version, stage)))
                .replaceWith(true);
    }

    Uni<VersionModel> getVersion(Long id) {
        final var getVersionServiceRequest = new GetVersionRoutedRequest(id);
        return versionModule.getVersionInternalService().getVersion(getVersionServiceRequest)
                .map(GetVersionInternalResponse::getVersion);
    }

    Uni<StageModel> getStage(Long tenantId, Long id) {
        final var request = new GetStageRoutedRequest(tenantId, id);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage);
    }

    Uni<Void> deployVersion(VersionModel version, StageModel stage) {
        stage.setVersionId(version.getId());
        version.setStatus(VersionStatusEnum.DEPLOYED);
        return syncStage(version.getTenantId(), stage)
                .flatMap(stageCreated -> syncVersion(version)
                        .replaceWithVoid())
                .onFailure(ServerSideClientErrorException.class)
                .invoke(t -> {
                    version.setStatus(VersionStatusEnum.FAILED);
                    version.setErrors(t.getMessage());
                });
    }

    Uni<Boolean> syncStage(Long tenantId, StageModel stage) {
        final var request = new SyncStageRoutedRequest(tenantId, stage);
        return tenantModule.getStageInternalService().syncStage(request)
                .map(SyncStageInternalResponse::getCreated);
    }

    Uni<Boolean> syncVersion(VersionModel version) {
        final var request = new SyncVersionRoutedRequest(version);
        return versionModule.getVersionInternalService().syncVersion(request)
                .map(SyncVersionInternalResponse::getCreated);
    }
}
