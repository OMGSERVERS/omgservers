package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperResponse;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.alias.CreateTenantProjectAliasOperation;
import com.omgservers.service.operation.alias.CreateTenantProjectAliasResult;
import com.omgservers.service.operation.authz.AuthorizeTenantRequestOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTenantProjectAliasMethodImpl implements CreateTenantProjectAliasMethod {

    final TenantShard tenantShard;
    final AliasShard aliasShard;
    final UserShard userShard;

    final CreateTenantProjectAliasOperation createTenantProjectAliasOperation;
    final AuthorizeTenantRequestOperation authorizeTenantRequestOperation;

    final AliasModelFactory aliasModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectAliasDeveloperResponse> execute(
            final CreateProjectAliasDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var userId = securityIdentity.<Long>getAttribute(
                SecurityAttributesEnum.USER_ID.getAttributeName());
        final var permission = TenantPermissionQualifierEnum.PROJECT_MANAGER;

        return authorizeTenantRequestOperation.execute(tenant, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantProjectId = request.getProjectId();
                    return getTenantProject(tenantId, tenantProjectId)
                            .flatMap(tenantProject -> {
                                final var aliasValue = request.getAlias();
                                return createTenantProjectAliasOperation.execute(tenantId, tenantProjectId, aliasValue);
                            });
                })
                .map(CreateTenantProjectAliasResult::created)
                .map(CreateProjectAliasDeveloperResponse::new);
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }
}
