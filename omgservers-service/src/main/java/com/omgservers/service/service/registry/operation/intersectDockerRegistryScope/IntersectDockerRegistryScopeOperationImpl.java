package com.omgservers.service.service.registry.operation.intersectDockerRegistryScope;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.service.registry.dto.DockerRegistryAccessDto;
import com.omgservers.service.service.registry.dto.DockerRegistryActionEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceScopeDto;
import com.omgservers.service.service.registry.dto.DockerRegistryResourceTypeEnum;
import com.omgservers.service.service.registry.dto.DockerRegistryScopeDto;
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

    final TenantShard tenantShard;

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
        final var tenantProjectId = resourceScope.getResourceName().getRepository().getTenantProjectId();

        return getTenantProject(tenantId, tenantProjectId)
                .flatMap(tenantProject -> viewTenantProjectPermissions(tenantId, tenantProjectId)
                        .map(tenantProjectPermissions -> {
                            final var userPermissions = tenantProjectPermissions
                                    .stream()
                                    .filter(permission -> permission.getUserId().equals(userId))
                                    .toList();

                            for (final var userPermission : userPermissions) {
                                switch (userPermission.getPermission()) {
                                    case VERSION_MANAGER -> {
                                        dockerRegistryAccess.getActions().add(DockerRegistryActionEnum.PULL);
                                        dockerRegistryAccess.getActions().add(DockerRegistryActionEnum.PUSH);
                                    }
                                }
                            }

                            return dockerRegistryAccess;
                        }));
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantShard.getService().getTenantProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

    Uni<List<TenantProjectPermissionModel>> viewTenantProjectPermissions(final Long tenantId,
                                                                         final Long tenantProjectId) {
        final var request = new ViewTenantProjectPermissionsRequest(tenantId, tenantProjectId);
        return tenantShard.getService().viewTenantProjectPermissions(request)
                .map(ViewTenantProjectPermissionsResponse::getTenantProjectPermissions);
    }
}