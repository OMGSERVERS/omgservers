package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateMatchmakerInternalRequest;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEntity;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEnum;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.GetProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.GetProjectInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.operation.getServersOperation.GetServersOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final InternalModule internalModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (StageCreatedEventBodyModel) event.getBody();
        final var tenant = body.getTenant();
        final var uuid = body.getUuid();
        return getStage(tenant, uuid)
                .flatMap(stage -> Uni.join().all(
                                syncPermissions(tenant, stage),
                                createMatchmaker(tenant, stage.getUuid(), stage.getMatchmaker()))
                        .andCollectFailures().replaceWithVoid())
                .replaceWith(true);
    }

    Uni<StageModel> getStage(UUID tenant, UUID uuid) {
        final var request = new GetStageInternalRequest(tenant, uuid);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage);
    }

    Uni<Void> syncPermissions(final UUID tenant, StageModel stage) {
        return getProject(tenant, stage.getProject())
                // TODO: sync permission for stage owner???
                .flatMap(project -> syncCreateVersionPermission(tenant, stage.getUuid(), project.getOwner()));
    }

    Uni<ProjectModel> getProject(UUID tenant, UUID uuid) {
        final var request = new GetProjectInternalRequest(tenant, uuid);
        return tenantModule.getProjectInternalService().getProject(request)
                .map(GetProjectInternalResponse::getProject);
    }

    Uni<Void> syncCreateVersionPermission(UUID tenant, UUID stage, UUID user) {
        final var permission = StagePermissionEntity.create(stage, user, StagePermissionEnum.CREATE_VERSION);
        final var request = new SyncStagePermissionInternalRequest(tenant, permission);
        return tenantModule.getStageInternalService().syncStagePermission(request)
                .replaceWithVoid();
    }

    Uni<Void> createMatchmaker(final UUID tenant, final UUID stage, final UUID uuid) {
        final var matchmaker = MatchmakerModel.create(uuid, tenant, stage);
        final var request = new CreateMatchmakerInternalRequest(matchmaker);
        return matchmakerModule.getMatchmakerInternalService().createMatchmaker(request);
    }
}
