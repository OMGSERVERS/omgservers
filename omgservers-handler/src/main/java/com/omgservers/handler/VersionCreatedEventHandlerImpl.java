package com.omgservers.handler;

import com.omgservers.module.version.VersionModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.dto.tenant.GetStageInternalResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageInternalResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.version.GetVersionShardedResponse;
import com.omgservers.dto.version.GetVersionShardedRequest;
import com.omgservers.dto.version.SyncVersionShardedResponse;
import com.omgservers.dto.version.SyncVersionShardedRequest;
import com.omgservers.exception.ServerSideClientErrorException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionStatusEnum;
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
        final var getVersionServiceRequest = new GetVersionShardedRequest(id);
        return versionModule.getVersionShardedService().getVersion(getVersionServiceRequest)
                .map(GetVersionShardedResponse::getVersion);
    }

    Uni<StageModel> getStage(Long tenantId, Long id) {
        final var request = new GetStageShardedRequest(tenantId, id);
        return tenantModule.getStageShardedService().getStage(request)
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
        final var request = new SyncStageShardedRequest(tenantId, stage);
        return tenantModule.getStageShardedService().syncStage(request)
                .map(SyncStageInternalResponse::getCreated);
    }

    Uni<Boolean> syncVersion(VersionModel version) {
        final var request = new SyncVersionShardedRequest(version);
        return versionModule.getVersionShardedService().syncVersion(request)
                .map(SyncVersionShardedResponse::getCreated);
    }
}
