package com.omgservers.application.handlers;

import com.omgservers.application.factory.MatchmakerModelFactory;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.operation.getServers.GetServersOperation;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.impl.factory.StagePermissionModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final InternalModule internalModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GetServersOperation getServersOperation;
    final GenerateIdOperation generateIdOperation;

    final StagePermissionModelFactory stagePermissionModelFactory;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (StageCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();
        return getStage(tenantId, id)
                .flatMap(stage -> Uni.join().all(
                                syncPermissions(tenantId, stage),
                                syncMatchmaker(tenantId, stage.getId(), stage.getMatchmakerId()))
                        .andCollectFailures().replaceWithVoid())
                .replaceWith(true);
    }

    Uni<StageModel> getStage(Long tenantId, Long id) {
        final var request = new GetStageShardRequest(tenantId, id);
        return tenantModule.getStageShardedService().getStage(request)
                .map(GetStageInternalResponse::getStage);
    }

    Uni<Void> syncPermissions(final Long tenantId, StageModel stage) {
        return getProject(tenantId, stage.getProjectId())
                // TODO: sync permission for stage owner???
                .flatMap(project -> syncCreateVersionPermission(tenantId, stage.getId(), project.getOwnerId()));
    }

    Uni<ProjectModel> getProject(Long tenantId, Long id) {
        final var request = new GetProjectShardRequest(tenantId, id);
        return tenantModule.getProjectShardedService().getProject(request)
                .map(GetProjectInternalResponse::getProject);
    }

    Uni<Void> syncCreateVersionPermission(Long tenantId, Long stageId, Long userId) {
        final var permission = stagePermissionModelFactory.create(stageId, userId, StagePermissionEnum.CREATE_VERSION);
        final var request = new SyncStagePermissionShardRequest(tenantId, permission);
        return tenantModule.getStageShardedService().syncStagePermission(request)
                .replaceWithVoid();
    }

    Uni<Void> syncMatchmaker(final Long tenantId, final Long stageId, final Long id) {
        final var matchmaker = matchmakerModelFactory.create(id, tenantId, stageId);
        final var request = new SyncMatchmakerShardRequest(matchmaker);
        return matchmakerModule.getMatchmakerInternalService()
                .syncMatchmaker(request)
                .replaceWithVoid();
    }
}
