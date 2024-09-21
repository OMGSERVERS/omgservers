package com.omgservers.service.service.registry.operation.intersectDockerRegistryScope;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.service.service.registry.dto.DockerRegistryAccessDto;
import com.omgservers.service.service.registry.dto.DockerRegistryActionEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceScopeDto;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceTypeEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryScopeDto;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IntersectDockerRegistryScopeOperationImpl implements IntersectDockerRegistryScopeOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<List<DockerRegistryAccessDto>> intersectDockerRegistryScope(final Long userId,
                                                                           final DockerRegistryScopeDto requestedScope) {
        final var resourceScopes = requestedScope.getResourceScopes();

        return Multi.createFrom().iterable(resourceScopes)
                .onItem().transformToUniAndConcatenate(resourceScope -> intersectResourceScope(userId, resourceScope))
                .collect().asList()
                .map(intersection -> intersection.stream()
                        .filter(dockerRegistryAccess -> !dockerRegistryAccess.getActions().isEmpty())
                        .toList());
    }

    Uni<DockerRegistryAccessDto> intersectResourceScope(final Long userId,
                                                        final DockerRegistryResourceScopeDto resourceScope) {
        final var dockerRegistryAccess = new DockerRegistryAccessDto();
        dockerRegistryAccess.setType(resourceScope.getResourceType());

        final var name = resourceScope.getResourceName().getRepository().toString();
        dockerRegistryAccess.setName(name);

        if (!resourceScope.getResourceType().equals(DockerRegistryResourceTypeEnum.REPOSITORY)) {
            return Uni.createFrom().item(dockerRegistryAccess);
        }

        final var namespace = resourceScope.getResourceName().getRepository().getNamespace();
        if (!namespace.equals("omgservers")) {
            return Uni.createFrom().item(dockerRegistryAccess);
        }

        final var tenantId = resourceScope.getResourceName().getRepository().getTenantId();
        final var projectId = resourceScope.getResourceName().getRepository().getProjectId();
        final var stageId = resourceScope.getResourceName().getRepository().getStageId();

        return getStage(tenantId, stageId)
                .flatMap(stage -> {
                    if (!stage.getProjectId().equals(projectId)) {
                        return Uni.createFrom().item(dockerRegistryAccess);
                    }

                    return viewStagePermissions(tenantId, stageId)
                            .map(stagePermissions -> {
                                final var userPermissions = stagePermissions.stream()
                                        .filter(permission -> permission.getUserId().equals(userId))
                                        .toList();

                                for (final var userPermission : userPermissions) {
                                    switch (userPermission.getPermission()) {
                                        case VERSION_MANAGEMENT -> {
                                            dockerRegistryAccess.getActions().add(DockerRegistryActionEnum.PULL);
                                            dockerRegistryAccess.getActions().add(DockerRegistryActionEnum.PUSH);
                                        }
                                    }
                                }

                                return dockerRegistryAccess;
                            });
                });
    }

    Uni<TenantStageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().getStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<List<TenantStagePermissionModel>> viewStagePermissions(final Long tenantId,
                                                               final Long stageId) {
        final var request = new ViewTenantStagePermissionsRequest(tenantId, stageId);
        return tenantModule.getTenantService().viewStagePermissions(request)
                .map(ViewTenantStagePermissionsResponse::getTenantStagePermissions);
    }
}