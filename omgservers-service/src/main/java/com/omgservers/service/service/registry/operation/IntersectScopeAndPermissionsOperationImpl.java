package com.omgservers.service.service.registry.operation;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.operation.authz.AuthorizeRegistryAccessOperation;
import com.omgservers.service.service.registry.dto.ParsedResourceScope;
import com.omgservers.service.service.registry.dto.RegistryResourceAccess;
import com.omgservers.service.service.registry.dto.RegistryResourceTypeEnum;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IntersectScopeAndPermissionsOperationImpl implements IntersectScopeAndPermissionsOperation {

    final TenantShard tenantShard;

    final AuthorizeRegistryAccessOperation authorizeRegistryAccessOperation;

    @Override
    public Uni<List<RegistryResourceAccess>> execute(final Long userId,
                                                     final List<ParsedResourceScope> parsedScope) {
        return Multi.createFrom().iterable(parsedScope)
                .onItem().transformToUniAndConcatenate(resourceScope -> intersect(userId, resourceScope))
                .collect().asList()
                .map(intersection -> intersection.stream()
                        .filter(registryResourceAccess -> !registryResourceAccess.actions().isEmpty())
                        .toList());
    }

    Uni<RegistryResourceAccess> intersect(final Long userId,
                                          final ParsedResourceScope resourceScope) {
        if (!resourceScope.resourceType().equals(RegistryResourceTypeEnum.REPOSITORY)) {
            return noAccess(resourceScope);
        }

        if (Objects.isNull(resourceScope.namespace())) {
            return noAccess(resourceScope);
        }

        final var tenant = resourceScope.namespace();
        final var project = resourceScope.image();
        final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGER;
        return authorizeRegistryAccessOperation.execute(tenant, project, userId, permission)
                .map(authorization -> RegistryResourceAccess.buildWriteAccess(resourceScope))
                .onFailure().recoverWithUni(f -> noAccess(resourceScope));
    }

    Uni<RegistryResourceAccess> noAccess(final ParsedResourceScope resourceScope) {
        return Uni.createFrom().item(RegistryResourceAccess.buildNoAccess(resourceScope));
    }
}