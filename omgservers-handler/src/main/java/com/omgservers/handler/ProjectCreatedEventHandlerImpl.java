package com.omgservers.handler;

import com.omgservers.module.user.UserModule;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.operation.getServers.GetServersOperation;
import com.omgservers.dto.tenant.GetProjectShardedResponse;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.factory.ProjectPermissionModelFactory;
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
        final var request = new GetProjectShardedRequest(tenantId, id);
        return tenantModule.getProjectShardedService().getProject(request)
                .map(GetProjectShardedResponse::getProject);
    }

    Uni<Void> syncCreateStagePermission(Long tenantId, Long projectId, Long userId) {
        final var permission = projectPermissionModelFactory.create(projectId, userId, ProjectPermissionEnum.CREATE_STAGE);
        final var request = new SyncProjectPermissionShardedRequest(tenantId, permission);
        return tenantModule.getProjectShardedService().syncProjectPermission(request)
                .replaceWithVoid();
    }
}
