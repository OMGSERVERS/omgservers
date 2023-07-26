package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.module.versionModule.model.VersionStatusEnum;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.application.exception.ServerSideClientErrorException;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.SyncVersionInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetVersionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

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
        final var getVersionServiceRequest = new GetVersionInternalRequest(id);
        return versionModule.getVersionInternalService().getVersion(getVersionServiceRequest)
                .map(GetVersionInternalResponse::getVersion);
    }

    Uni<StageModel> getStage(Long tenantId, Long id) {
        final var request = new GetStageInternalRequest(tenantId, id);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage);
    }

    Uni<Void> deployVersion(VersionModel version, StageModel stage) {
        stage.setVersionId(version.getId());
        version.setStatus(VersionStatusEnum.DEPLOYED);
        return syncStage(version.getTenantId(), stage)
                .onFailure(ServerSideClientErrorException.class)
                .invoke(t -> {
                    version.setStatus(VersionStatusEnum.FAILED);
                    version.setErrors(t.getMessage());
                })
                .flatMap(voidItem -> syncVersion(version));
    }

    Uni<Void> syncStage(Long tenantId, StageModel stage) {
        final var request = new SyncStageInternalRequest(tenantId, stage);
        return tenantModule.getStageInternalService().syncStage(request);
    }

    Uni<Void> syncVersion(VersionModel version) {
        final var request = new SyncVersionInternalRequest(version);
        return versionModule.getVersionInternalService().syncVersion(request);
    }
}
