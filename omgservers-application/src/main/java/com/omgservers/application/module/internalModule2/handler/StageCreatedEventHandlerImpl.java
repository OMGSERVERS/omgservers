package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.application.factory.MatchmakerModelFactory;
import com.omgservers.application.factory.StagePermissionModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.base.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.base.operation.generateId.GenerateIdOperation;
import com.omgservers.base.operation.getServers.GetServersOperation;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionRoutedRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionEnum;
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
        final var request = new GetStageRoutedRequest(tenantId, id);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage);
    }

    Uni<Void> syncPermissions(final Long tenantId, StageModel stage) {
        return getProject(tenantId, stage.getProjectId())
                // TODO: sync permission for stage owner???
                .flatMap(project -> syncCreateVersionPermission(tenantId, stage.getId(), project.getOwnerId()));
    }

    Uni<ProjectModel> getProject(Long tenantId, Long id) {
        final var request = new GetProjectRoutedRequest(tenantId, id);
        return tenantModule.getProjectInternalService().getProject(request)
                .map(GetProjectInternalResponse::getProject);
    }

    Uni<Void> syncCreateVersionPermission(Long tenantId, Long stageId, Long userId) {
        final var permission = stagePermissionModelFactory.create(stageId, userId, StagePermissionEnum.CREATE_VERSION);
        final var request = new SyncStagePermissionRoutedRequest(tenantId, permission);
        return tenantModule.getStageInternalService().syncStagePermission(request)
                .replaceWithVoid();
    }

    Uni<Void> syncMatchmaker(final Long tenantId, final Long stageId, final Long id) {
        final var matchmaker = matchmakerModelFactory.create(id, tenantId, stageId);
        final var request = new SyncMatchmakerRoutedRequest(matchmaker);
        return matchmakerModule.getMatchmakerInternalService()
                .syncMatchmaker(request)
                .replaceWithVoid();
    }
}
