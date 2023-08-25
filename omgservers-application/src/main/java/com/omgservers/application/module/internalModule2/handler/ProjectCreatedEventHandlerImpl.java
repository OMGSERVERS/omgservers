package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.factory.ProjectPermissionModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.base.impl.operation.getServersOperation.GetServersOperation;
import com.omgservers.dto.tenantModule.GetProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectCreatedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GetServersOperation getServersOperation;
    final GenerateIdOperation generateIdOperation;

    final ProjectPermissionModelFactory projectPermissionModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PROJECT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ProjectCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();
        return getProject(tenantId, id)
                .flatMap(project -> syncCreateStagePermission(tenantId, project.getId(), project.getOwnerId()))
                .replaceWith(true);
    }

    Uni<ProjectModel> getProject(Long tenantId, Long id) {
        final var request = new GetProjectInternalRequest(tenantId, id);
        return tenantModule.getProjectInternalService().getProject(request)
                .map(GetProjectInternalResponse::getProject);
    }

    Uni<Void> syncCreateStagePermission(Long tenantId, Long projectId, Long userId) {
        final var permission = projectPermissionModelFactory.create(projectId, userId, ProjectPermissionEnum.CREATE_STAGE);
        final var request = new SyncProjectPermissionInternalRequest(tenantId, permission);
        return tenantModule.getProjectInternalService().syncProjectPermission(request)
                .replaceWithVoid();
    }
}
