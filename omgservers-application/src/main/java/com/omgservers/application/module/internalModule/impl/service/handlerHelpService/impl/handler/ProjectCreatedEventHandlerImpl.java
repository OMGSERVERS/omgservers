package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectPermissionEnum;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.GetProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.GetProjectInternalResponse;
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
public class ProjectCreatedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PROJECT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ProjectCreatedEventBodyModel) event.getBody();
        final var tenant = body.getTenant();
        final var uuid = body.getUuid();
        return getProject(tenant, uuid)
                .flatMap(project -> syncCreateStagePermission(tenant, project.getUuid(), project.getOwner()))
                .replaceWith(true);
    }

    Uni<ProjectModel> getProject(UUID tenant, UUID uuid) {
        final var request = new GetProjectInternalRequest(tenant, uuid);
        return tenantModule.getProjectInternalService().getProject(request)
                .map(GetProjectInternalResponse::getProject);
    }

    Uni<Void> syncCreateStagePermission(UUID tenant, UUID project, UUID user) {
        final var permission = ProjectPermissionModel.create(project, user, ProjectPermissionEnum.CREATE_STAGE);
        final var request = new SyncProjectPermissionInternalRequest(tenant, permission);
        return tenantModule.getProjectInternalService().syncProjectPermission(request)
                .replaceWithVoid();
    }
}
