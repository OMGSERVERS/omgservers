package com.omgservers.module.developer.impl.service.developerService.impl.method.createProject;

import com.omgservers.module.user.UserModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.factory.ProjectModelFactory;
import com.omgservers.factory.StageModelFactory;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateProjectMethodImpl implements CreateProjectMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final ProjectModelFactory projectModelFactory;
    final StageModelFactory stageModelFactory;

    final GenerateIdOperation generateIdOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(final CreateProjectDeveloperRequest request) {
        CreateProjectDeveloperRequest.validate(request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var tenantId = request.getTenantId();

        return checkCreateProjectPermission(tenantId, userId)
                .call(voidItem -> checkTenant(tenantId))
                .flatMap(voidItem -> createProject(tenantId, userId));
    }

    Uni<Void> checkCreateProjectPermission(final Long tenantId, final Long userId) {
        final var permission = TenantPermissionEnum.CREATE_PROJECT;
        final var hasTenantPermissionServiceRequest = new HasTenantPermissionShardedRequest(tenantId, userId, permission);
        return tenantModule.getTenantShardedService().hasTenantPermission(hasTenantPermissionServiceRequest)
                .map(HasTenantPermissionShardedResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenantId=%s, userId=%s, permission=%s", tenantId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> checkTenant(final Long tenantId) {
        final var getTenantServiceRequest = new GetTenantShardedRequest(tenantId);
        return tenantModule.getTenantShardedService().getTenant(getTenantServiceRequest)
                .replaceWithVoid();
    }

    Uni<CreateProjectDeveloperResponse> createProject(final Long tenantId, final Long userId) {
        final var project = projectModelFactory.create(tenantId, userId, ProjectConfigModel.create());
        final var syncProjectInternalRequest = new SyncProjectShardedRequest(project);
        return tenantModule.getProjectShardedService().syncProject(syncProjectInternalRequest)
                .flatMap(response -> {
                    final var stage = stageModelFactory.create(project.getId(),
                            generateIdOperation.generateId(),
                            new StageConfigModel());
                    final var syncStageInternalRequest = new SyncStageShardedRequest(tenantId, stage);
                    return tenantModule.getStageShardedService().syncStage(syncStageInternalRequest)
                            .replaceWith(new CreateProjectDeveloperResponse(project.getId(), stage.getId(), stage.getSecret()));
                });
    }
}
