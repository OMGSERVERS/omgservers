package com.omgservers.service.server.registry.operation;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.operation.authz.AuthorizeRegistryAccessOperation;
import com.omgservers.service.server.registry.dto.ParsedResourceScope;
import com.omgservers.service.server.registry.dto.RegistryResourceAccess;
import com.omgservers.service.server.registry.dto.RegistryResourceTypeEnum;
import com.omgservers.service.shard.user.UserShard;
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

    final UserShard userShard;

    final AuthorizeRegistryAccessOperation authorizeRegistryAccessOperation;

    @Override
    public Uni<List<RegistryResourceAccess>> execute(final Long userId,
                                                     final List<ParsedResourceScope> parsedScope) {
        return getUser(userId)
                .flatMap(user -> Multi.createFrom().iterable(parsedScope)
                        .onItem().transformToUniAndConcatenate(resourceScope -> intersect(user, resourceScope))
                        .collect().asList()
                        .map(intersection -> intersection.stream()
                                .filter(registryResourceAccess -> !registryResourceAccess.actions().isEmpty())
                                .toList()));
    }

    Uni<UserModel> getUser(final Long id) {
        final var request = new GetUserRequest(id);
        return userShard.getService().execute(request)
                .map(GetUserResponse::getUser);
    }

    Uni<RegistryResourceAccess> intersect(final UserModel user,
                                          final ParsedResourceScope resourceScope) {
        if (!resourceScope.resourceType().equals(RegistryResourceTypeEnum.REPOSITORY)) {
            return noAccess(resourceScope);
        }

        if (Objects.isNull(resourceScope.namespace())) {
            return noAccess(resourceScope);
        }

        return switch (user.getRole()) {
            case UserRoleEnum.SERVICE, UserRoleEnum.SUPPORT ->
                    Uni.createFrom().item(RegistryResourceAccess.buildReadAccess(resourceScope));
            case UserRoleEnum.DEVELOPER -> {
                final var tenant = resourceScope.namespace();
                final var project = resourceScope.image();
                final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGER;
                final var userId = user.getId();
                yield authorizeRegistryAccessOperation.execute(tenant, project, userId, permission)
                        .map(authorization -> RegistryResourceAccess.buildWriteAccess(resourceScope))
                        .onFailure().recoverWithUni(f -> noAccess(resourceScope));
            }
            default -> noAccess(resourceScope);
        };
    }

    Uni<RegistryResourceAccess> noAccess(final ParsedResourceScope resourceScope) {
        return Uni.createFrom().item(RegistryResourceAccess.buildNoAccess(resourceScope));
    }
}